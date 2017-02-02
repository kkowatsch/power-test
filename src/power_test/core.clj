(ns power_test.core
  (:use (incanter core charts datasets)
        (incanter core stats charts io)
        (incanter core charts excel)))

(with-data ($rollup :mean :count [:population :fertility :mortality :migration])
  (read-dataset "http://www.census.gov/population/international/data/idb/region.php?N=%20Results%20&T=13&A=separate&RT=0&Y=2017&R=-1&C=CA"
             :header true)
 (view $data)
 (view (bar-chart :years :indicators))
 :group-by :mortality
 :legend true)

(view (bar-chart ["a" "b" "c" "d" "e"]
                 [10 20 30 25 20]))

(view (bar-chart ["a" "a" "b" "b" "c" "c"]
                 [10 20 30 10 40 20]
                 :legend true
                 :group-by ["I" "II" "I" "II" "I" "II"]))

(def data (read-dataset "http://www.census.gov/population/international/data/idb/region.php?N=%20Results%20&T=13&A=separate&RT=0&Y=2017&R=-1&C=CA"))
(def by-year (group-by data 0))
(view (bar-chart (sel (last by-year) :cols 2)
                 (sel (last by-year) :cols 1)
                 :title "Airline Travel in 1960"
                 :y-label "Passengers"
                 :x-label "Month"))
