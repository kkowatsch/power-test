(defproject power-test "0.1.0-SNAPSHOT"
  :description "Front-End Development test for Powernoodle inc."
  :url "https://github/kkowatsch/power-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [proto-repl "0.3.1"]
                 [proto-repl-charts "0.3.2"]
                 [incanter "1.5.7"]]

    :main ^:skip-aot power_test.core
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}})
