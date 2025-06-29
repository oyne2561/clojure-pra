(ns clojure-app.markdown-parser
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def markdown-file-path "resources/sample.md")

(defn create-sample-file []
  (let [content "# タイトル
これは普通のテキストです。
**これは太文字です**
普通のテキスト続き
**別の太文字**と普通のテキスト
## サブタイトル
**複数の** **太文字が** **ある行**
最後の行"]
    (.mkdirs (java.io.File. "resources"))
    (spit markdown-file-path content)))

(create-sample-file)

(def bold-pattern #"\*\*([^*]+\*\*)")

(defn find-bold-in-project-file []
  (try
    (with-open [reader (io/reader markdown-file-path)]
      (->> (line-seq reader)
           (map-indexed vector)  ; [行番号 行内容] のペアを作成
           (map (fn [[line-num line]]
                  (let [matches (re-seq bold-pattern line)]
                    (when (seq matches)
                      {:line-number (inc line-num)  ; 1から始まる行番号
                       :line-content line
                       :bold-texts (map second matches)}))))  ; キャプチャグループの内容
           (filter some?)  ; nilを除外
           (doall)))
    (catch java.io.FileNotFoundException e
      (println (str "ファイルが見つかりません: " markdown-file-path))
      (println "create-sample-file関数を実行してファイルを作成してください")
      [])
    (catch Exception e
      (println (str "エラーが発生しました: " (.getMessage e)))
      [])))

;; 実行して結果を確認
(def result (find-bold-in-project-file))
(println "=== プロジェクトファイルからの太文字検出結果 ===")
(doseq [item result]
  (println (format "行 %d: %s" (:line-number item) (:line-content item)))
  (println (format "  太文字: %s" (str/join ", " (:bold-texts item))))
  (println))

;; 複数のMarkdownファイルを処理する関数
(defn process-multiple-files [file-paths]
  (for [file-path file-paths]
    (let [results (try
                    (with-open [reader (io/reader file-path)]
                      (->> (line-seq reader)
                           (map-indexed vector)
                           (map (fn [[line-num line]]
                                  (let [matches (re-seq bold-pattern line)]
                                    (when (seq matches)
                                      {:line-number (inc line-num)
                                       :line-content line
                                       :bold-texts (map second matches)}))))
                           (filter some?)
                           (doall)))
                    (catch Exception e
                      (println (str "エラー in " file-path ": " (.getMessage e)))
                      []))]
      {:file-path file-path
       :results results
       :bold-count (count results)})))

;; 追加のテストファイルを作成
(defn create-additional-files []
  (spit "resources/readme.md" "# README\n**重要**: このプロジェクトについて\n普通のテキスト\n**注意事項**もあります")
  (spit "resources/notes.md" "# メモ\n**TODO**: 実装予定\n- リスト項目\n**完了**: 基本機能")
  (println "追加ファイルを作成しました"))

(create-additional-files)

;; 複数ファイルを処理
(def multi-results (process-multiple-files ["resources/sample.md" "resources/readme.md" "resources/notes.md"]))

(println "=== 複数ファイルの処理結果 ===")
(doseq [{:keys [file-path results bold-count]} multi-results]
  (println (format "\n📁 %s (太文字: %d個)" file-path bold-count))
  (doseq [item results]
    (println (format "  行 %d: %s" (:line-number item) (str/join ", " (:bold-texts item))))))
