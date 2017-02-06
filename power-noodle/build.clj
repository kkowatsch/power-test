(require 'cljs.build.api)

(cljs.build.api/build "src" {:output-to "resources/js/script.js"})
