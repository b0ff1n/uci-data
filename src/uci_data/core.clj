(ns uci-data.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:gen-class))

(def RED ">50K.")
(def BLUE "<=50K")


(defn read-dataset [dataset training?]
  (let [filename (str dataset "." (if training? "data.csv" "test.csv"))
        path (str "datasets/" dataset "/" filename)]
    (with-open [in (io/reader path)]
      (doall
       (csv/read-csv in)))))


(defn prediction-random [train test]
  (reduce (fn [result item]
            (let [r (= (last item) (rand-nth [RED BLUE]))
                  c (get result r 0)]
              (assoc result r (inc c)))) {true 0 false 0} test))


(defn succeeds% [res]
  (let [t (get res true)
        f (get res false)]
    (float (* 100 (/ t (+ t f))))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



(->> (rest (read-dataset "adult" false))
     (map last)
     (distinct))

(let [test (rest (read-dataset "adult" false))]
  (prediction-random '() test))




(->> (read-dataset "adult" false)
     (rest)
     (prediction-random '())
     (succeeds%))
