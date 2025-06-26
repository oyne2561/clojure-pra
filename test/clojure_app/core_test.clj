(ns clojure-app.core-test
  (:require [clojure.test :refer :all]
            [clojure-app.core :refer :all]))

(defn test-string-lengths [description text expected-codepoints expected-chars]
  "文字列の長さをテストするヘルパー関数"
  (testing description
    (is (= expected-codepoints (codepoint-count text))
        (str "コードポイント数が期待値と異なります: " text))
    (is (= expected-chars (char-count text))
        (str "char数が期待値と異なります:" text))))

(deftest basic-string-test
  (test-string-lengths "英語の基本文字列" "Hello" 5 5))