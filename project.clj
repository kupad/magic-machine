(defproject magic_machine "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [simple-plotter "0.1.2"]]
  :main ^:skip-aot magic-machine.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
