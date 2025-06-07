(ns clojure-app.core
  (:gen-class))

(defn greet [name]
  (str "こんにちは、" name "さん！"))

(greet "テスト")

;; :simple-keyword
(keyword "dynamic-keyword")

(name :user-name)

(def user {:name "太郎"
           :age 25
           :email "taro@example.com"
           :is-active? true})
(:name user)
(:age user)
(:phone user "未設定")

#_{:clj-kondo/ignore [:unused-binding]}
(defn -main
  "アプリケーションのエントリーポイント"
  [& args]
  (println (greet "世界"))
  (println "Clojureアプリが動いています！"))


(def company {:name "ABC Corp"
              :employees [{:name "taro" :dept :engineering}
                          {:name "花子" :dept :sales}]})

(:name company)
(get company :name)

(defn hello
  "Writes hello message to *out*. Calls you by username"
  [username]
  (println (str "Hello, " username)))

(hello "佐藤")

(defn hello2 [username]
  (println (str "Hello2, " username)))

(hello2 "ppp")