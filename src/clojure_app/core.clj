(ns clojure-app.core)

(defn codepoint-count [s]
  "文字列の正確なコードポイント数を返す"
  (.codePointCount s 0 (.length s)))

(defn char-count [s]
  "文字列のchar数を返す(サロゲートペアで問題が起きる)"
  (count s))

(defn length-equals? [s expected-length]
  "文字列の長さが期待値と等しいかを確認する"
  (= (codepoint-count s) expected-length))


(def normal-text "Hello")
(println "通常の文字列:" normal-text)
(println "コードポイント数:" (codepoint-count normal-text))
(println "char数:" (char-count normal-text))

;; 日本語文字列
(def japanese-text "こんにちは")
(println "\n日本語文字列:" japanese-text)
(println "コードポイント数:" (codepoint-count japanese-text))
(println "char数:" (char-count japanese-text))

;; 絵文字を含む文字列（サロゲートペアの例）
(def emoji-text "Hello😀")
(println "\n絵文字を含む文字列:" emoji-text)
(println "コードポイント数:" (codepoint-count emoji-text))
(println "char数:" (char-count emoji-text))

;; 複雑な絵文字の例
(def complex-emoji "👨‍👩‍👧‍👦")
(println "\n複雑な絵文字:" complex-emoji)
(println "コードポイント数:" (codepoint-count complex-emoji))
(println "char数:" (char-count complex-emoji))

;; 文字列長のチェック
(defn validate-input [input max-length]
  "入力文字列の長さをチェック"
  (if (length-equals? input max-length)
    (println (str "✓ 入力「" input "」は" max-length "文字です"))
    (println (str "✗ 入力「" input "」は" max-length "文字ではありません（実際は" (codepoint-count input) "文字）"))))

;; テスト実行
(validate-input "Hello" 5)
(validate-input "Hello😀" 6)
(validate-input "Hello😀" 7)

;; Twitter風の文字数制限チェック
(defn tweet-length-check [text]
  "ツイートの文字数制限をチェック（280文字）"
  (let [length (codepoint-count text)]
    (cond
      (<= length 280) (println (str "✓ 投稿可能です（" length "/280文字）"))
      :else (println (str "✗ 文字数制限を超えています（" length "/280文字）")))))

;; テスト
(tweet-length-check "普通のテキストです")
(tweet-length-check "絵文字付きのテキストです😀🎉")

;; 長いテキストの例
(def long-text (apply str (repeat 290 "あ")))
(tweet-length-check long-text)