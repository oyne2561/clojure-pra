(ns clojure-app.core
  (:gen-class))

(defn greet [name]
  (str "こんにちは、" name "さん！"))

#_{:clj-kondo/ignore [:unused-binding]}
(defn -main
  "アプリケーションのエントリーポイント"
  [& args]
  (println (greet "世界"))
  (println "Clojureアプリが動いています！"))