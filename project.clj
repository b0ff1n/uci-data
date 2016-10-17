(defproject uci-data "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.3"]
                 [judgr "0.3.0"]]
  :main ^:skip-aot uci-data.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
