; (ns power_test.core
;   (:use [incanter core stats charts io]
;         [incanter-svg]))
;
; (defn can-data
;   (-> (format "http://www.census.gov/population/international/data/idb/region.php?N=%20Results%20&T=13&A=separate&RT=1&Y=2017&R=60&C=CA")(read-dataset :header true))
;
;  (view (bar-chart   :Type :uptake
;                      :title "Canada Demographic Overview"
;                      :group-by :Treatment
;                      :x-label "Demographic" :y-label "Year"
;                     :legend true)))
(ns power_test.core
  (:gen-class)
  (:use (incanter core stats charts datasets)))

(def -main [& args]
  (save (bar-chart   :data (read-data "http://www.census.gov/population/international/data/idb/informationGateway.php#DOWNLOAD")
                     :header true
                     :title "Demographic Overview"
                     :x-label "Countries" :y-label "Year"
                     :legend true)))
