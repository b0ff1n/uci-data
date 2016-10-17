(ns uci-data.classifiers
  (:require [judgr.core :as jc]
            [judgr.settings :as js]
            [judgr.extractor.base :as jb]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))


(def ^:const RESULTS [:rich :poor])


(deftype MyExtractor [settings]
  jb/FeatureExtractor
  (extract-features [fe item]
                    item))
(defmethod jc/extractor-from :my [settings]
  (MyExtractor. settings))


(defprotocol Classifier
  (train [this attrs result])
  (classify [this attrs]))



(defrecord RandomClassifier []
  Classifier
  (train [this attrs result]
         true)
  (classify [this attrs]
            (rand-nth RESULTS)))


(defrecord NaiveBayesClassifier [classifier]
  Classifier
  (train [{classifier :classifier} attrs result]
         (.train! classifier attrs result))
  (classify [{classifier :classifier} attrs]
            (.classify classifier attrs)))



(defn random-classifier []
  (->RandomClassifier))


(defn naive-bayes-classifier []
  (let [nb-settings {:classes RESULTS
                     :unknown-class :unknown
                     :extractor {:type :my}
                     :database {:type :memory}
                     :classifier {:type :default
                                  :default {:unbiased? false
                                            :smoothing-factor 1
                                            :threshold? true
                                            :thresholds {:poor 1.2, :rich 2.5}}}}
        classifier (jc/classifier-from nb-settings)]
    (->NaiveBayesClassifier classifier)))
