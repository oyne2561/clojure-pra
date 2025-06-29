(ns clojure-app.seq
  (:require [clojure.java.io :as io]))

(def sample-text "Hello World\nこんにちは\n世界\nClojure\nプログラミング")


(with-open [reader (io/reader (java.io.StringReader. sample-text))]
  (doall (line-seq reader)))