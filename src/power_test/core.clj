(ns power_test.core
  (:use (incanter core stats charts io)))

(def data (read-data "http://www.census.gov/population/international/data/idb/informationGateway.php#DOWNLOAD"
           :header true))
(view data)

(column-names data)

(with-data data
  (def bar (bar-chart ($ :population) ($ :year))))
