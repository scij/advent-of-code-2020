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

(defn read-file [fn]
  (map #(Integer/parseInt %)
       (s/split (slurp fn) #"\n")))

(defn find-sum-from-here [target values]
  (loop [lwb (first values)
         upb (first values)
         val (first values)
         sum 0
         new-vals values]
    (let [n-sum (+ sum val)
          n-lwb (min val lwb)
          n-upb (max val upb)]
      (cond
        (= n-sum target) [n-lwb n-upb]
        (> n-sum target) nil
        (empty? (rest new-vals)) nil
        (< n-sum target)
        (recur n-lwb n-upb (second new-vals) n-sum (rest new-vals))
        )
      )
    )
  )

(defn try-find-sum [target values]
  (if-let [result (find-sum-from-here target values)]
    (reduce + result)
    (recur target (next values))
    )
  )