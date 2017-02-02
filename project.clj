(defproject power-test "0.1.0-SNAPSHOT"
  :description "Front-End Development test for Powernoodle inc."
  :url "https://github/kkowatsch/power-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [incanter "1.5.7"]
                 [proto-repl "0.3.1"]]
  :main ^:skip-aot power_test.core
  :plugins [[lein-midje "3.0.0"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :source-paths ["dev" "src" "test"]
             :dependencies [[org.clojure/tools.namespace "0.2.11"]]})
