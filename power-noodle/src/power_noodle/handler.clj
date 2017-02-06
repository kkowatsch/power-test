(ns power-noodle.handler
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.string :as str])

  (:use compojure.core
        ring.adapter.jetty
        [ring.middleware.content-type :only (wrap-content-type)]
        [ring.middleware.file :only (wrap-file)]
        [ring.middleware.file-info :only (wrap-file-info)]
        [ring.middleware.stacktrace :only (wrap-stacktrace)]
        [ring.util.response :only (redirect)]
        [hiccup core element page]
        [hiccup.middleware :only (wrap-base-url)]))

(defn d3-page
  [title js body & {:keys [extra-js] :or {extra-js []}}]
  (html5
    [:head
     [:title title]
     (include-css "/css/style.css")]
    [:body
     (concat
       [body]
       [(include-js "https://d3js.org/d3.v4.min.js")]
       (map include-js extra-js)
       [(include-js "/js/script.js")
        (javascript-tag js)])]))

;;; A group of values. Each group has a key/label and
;;; a JS array of point values.
(deftype Group [key values])
;;; A point. Each point has a location (x, y) and a
;;; size.
(deftype Point [x y size])

;;; This sets an axis' label if not nil.
(defn add-label
  [chart axis label]
  (if-not (nil? label)
    (.axisLabel (aget chart axis) label)))
;;; Add axes' labels to the chart.
(defn add-axes-labels [chart x-label y-label]
  (doto chart
    (add-label "xAxis" x-label)
    (add-label "yAxis" y-label)))
;;; This builds the chart from the selector.
(defn populate-node
  [selector chart groups transition continuation]
  (-> (.select js/d3 selector)
    (.datum groups)
    (.transition)
    (.duration (if transition 500 0))
    (.call chart)
    (.call continuation)))

(defn bar-chart []
  (d3-page "Bar Chart"
           "powernoodle.barchart.bar_chart();"
           [:div#barchart.chart [:svg]]))

(defroutes
  site-routes
  (GET "/" [] (index-page))
  (GET "/ibm.json" [] (ibm-json))

  (GET "/barchart" [] (bar-chart))
  (GET "/barchart/data.json" []
       (redirect "https://www.census.gov/population/international/data/idb/region.php?N=%20Results%20&T=13&A=separate&RT=0&Y=2017&R=-1&C=CA"))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site site-routes)
    (wrap-base-url)
    (wrap-file "resources")
    (wrap-file-info)
    (wrap-content-type)))

(defn -main
  []
  (run-jetty app {:port 3000}))
