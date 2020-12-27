(ns advent-of-code-2020.door09
  (:require [clojure.math.combinatorics :as c]
            [clojure.string :as s]))

(defn assert-sum [curr preamble]
  (some
    #(= curr (reduce + %))
     (map vec (c/combinations preamble 2))
    )
  )

(defn check-first-seq [s]
  (let [num (last (first s))
        preamble (butlast (first s))]
    (if (assert-sum num preamble)
      (check-first-seq (rest s))
      (println num)
      )
    )
  )

(defn check-sum-of-two [preamble-len numbers]
  (check-first-seq (partition (inc preamble-len) 1 numbers))
  )

(defn find-sum [sum numbers]

  )
(defn read-file [fn]
  (map #(Integer/parseInt %)
       (s/split (slurp fn) #"\n")))