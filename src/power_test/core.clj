(ns power_test.core
  (:use
    midje.semi-sweet
    incanter.core
    incanter.charts
    clojure.test
    clojure.contrib.str-utils
    [clojure.contrib.duck-streams :only (read-lines)])
  (:import org.joda.time.format.DateTimeFormat))

(defn extract-records-from-line
  [line-from-access-log]
  (let [[_ ip username date] (re-find #"^(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}) - (\w+) (.+? .+?) " line-from-access-log)]
    [date username]))

(defn as-dataseries
  [access-log-lines]
  (remove #(= [nil nil] %) (map extract-records-from-line access-log-lines)))

(defn records-from-access-log
  [filename]
  (as-dataseries (read-lines filename)))

(defn as-millis
  [date-as-str]
  (print (str "Formatting " date-as-str "\n"))
  (.getMillis (.parseDateTime (DateTimeFormat/forPattern "dd/MMM/yyyy:HH:mm:ss Z") date-as-str)))

(defn round-ms-down-to-nearest-sec
  [millis]
  (* 1000 (quot millis 1000)))

(defn round-ms-down-to-nearest-min
  [millis]
  (* 60000 (quot millis 60000)))

(defn access-log-to-dataset
  [filename]
  (let [unmod-dataset (col-names (to-dataset (records-from-access-log filename)) ["Date" "User"])]
    ($rollup :sum "Hits" "Date" (col-names (conj-cols ($map #(round-ms-down-to-nearest-sec (as-millis %)) "Date" unmod-dataset) (repeat 1)) ["Date" "Hits"]))))

(defn hit-graph
  [dataset]
  (time-series-plot :Date :Hits
                             :x-label "Date"
                             :y-label "Hits"
                             :title "Hits Per Second"
                             :data dataset))

(def example-record
  "10.44.137.100 - someguy 05/Aug/2010:17:27:24 +0100 \"GET /someurl HTTP/1.1\" 200 24 \"http://refering.site.com/\" \"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9.0.9) Gecko/2009040821 Firefox/3.0.9 (.NET CLR 3.5.30729)\"")

(deftest should-split-log-line-into-records
  (expect (as-dataseries [example-record]) => '(("05/Aug/2010:17:27:24 +0100" "someguy"))))
(deftest should-ignore-bad-log-rows
  (expect (as-dataseries ["I am a bogus record"]) => '()))
(deftest should-split-multiple-log-line-into-records
  (expect (as-dataseries [example-record example-record]) => '(("05/Aug/2010:17:27:24 +0100" "someguy"), ("05/Aug/2010:17:27:24 +0100" "someguy"))))
(deftest should-reformat-date-as-millis
  (expect (as-millis "05/Aug/2010:17:27:24 +0100") => 1281025644000))
(deftest should-round-millis-down-to-nearest-sec
  (expect (round-ms-down-to-nearest-sec 0) => 0)
  (expect (round-ms-down-to-nearest-sec 1001) => 1000)
  (expect (round-ms-down-to-nearest-sec 1999) => 1000))
(deftest should-round-millis-down-to-nearest-min
  (expect (round-ms-down-to-nearest-min 0) => 0)
  (expect (round-ms-down-to-nearest-min 61001) => 60000)
  (expect (round-ms-down-to-nearest-min 61999) => 60000))
(run-tests 'com.thoughtworks.loganalysis.loganalysis)
