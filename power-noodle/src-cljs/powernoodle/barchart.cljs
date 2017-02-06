(ns powernoodle.barchart
  (:require [powernoodle.core :as powernoodle]))

(defn count-point [pair]
  (let [[years items] pair]
    (powernoodle/Point. years (count items) 1)))
(defn get-years-counts [years-groups]
  (apply array (map count-point years-groups)))

(defn indicators-point [pair]
  (let [[years items] pair
        indicators-total (sum-by #(.-indicators %) items)]
    (powernoodle/Point. years indicators-total 1)))
(defn get-years-indicatorss [years-groups]
  (apply array (map indicators-point years-groups)))

(defn json->nv-groups [json]
  (let [years-groups (group-by #(.-years %) json)]
    (array (powernoodle/Group.
             "Years"
             (get-years-counts years-groups))
           (powernoodle/Group.
             "Demographic Indicators"
             (get-years-indicatorss years-groups)))))

(defn ^:export bar-chart []
  (powernoodle/create-chart "/barchart/data.json"
                       "#barchart svg"
                       #(.multiBarChart js/nv.models)
                       json->nv-groups))
