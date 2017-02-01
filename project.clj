(defproject power-test "0.1.0-SNAPSHOT"
  :description "Front-End Development tesst for Powernoodle inc."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [incanter "1.5.2"]
                 [proto-repl "0.3.1"]]
  :main ^:skip-aot power_test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
