(defproject power-noodle "0.1.0-SNAPSHOT"
  :description "Front-End Development test for Powernoodle inc."
  :url "https://github/kkowatsch/power-test"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [proto-repl "0.3.1"]
                 [ring/ring-core "1.1.7"]
                 [ring/ring-jetty-adapter "1.1.7"]
                 [compojure "1.1.3"]
                 [ring/ring-defaults "0.2.3"]
                 [org.clojure/clojurescript "1.9.456"]
                 [hiccup "1.0.5"]]

  :plugins [[lein-ring "0.7.5"]
            [lein-cljsbuild "0.2.10"]]
  :ring {:handler power-noodle.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}}

  :cljsbuild {:crossovers [power-noodle.x-over],
              :builds
              [{:source-paths ["src-cljs"],
                :crossover-path "xover-cljs",
                :compiler
                {:pretty-print true,
                 :output-to "resources/js/script.js",
                 :optimizations :whitespace}}]})
