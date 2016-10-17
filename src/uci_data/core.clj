(ns uci-data.core
  (:require [uci-data.classifiers :as cl]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:gen-class))

(def RED ">50K.")
(def BLUE "<=50K.")







(defn read-dataset [dataset training?]
  (let [filename (str dataset "." (if training? "data.csv" "test.csv"))
        path (str "datasets/" dataset "/" filename)]
    (with-open [in (io/reader path)]
      (doall
       (csv/read-csv in)))))


(defn attributes [v]
  (butlast v))
(defn result [v]
  (let [r (last v)]
    (if (= r RED) :rich :poor)))



(defn classifier-tester [classifier train-ds test-ds]
  (doseq [v train-ds]
    (cl/train classifier (attributes v) (result v)))
  (frequencies (pmap #(= (cl/classify classifier (attributes %))
                        (result %))
                    test-ds)))



(def TRAIN_DATA (rest (read-dataset "adult" true)))
(def TEST_DATA (rest (read-dataset "adult" false)))


;; (let [rcl (cl/random-classifier)]
;;   (classifier-tester rcl TRAIN_DATA TEST_DATA))
(succeeds% {true 8231, false 8050})

;; (let [nbcl (cl/naive-bayes-classifier)]
;;   (classifier-tester nbcl TRAIN_DATA TEST_DATA))
(succeeds% {true 12435, false 3846})
