(ns advent-of-code-2020.door18
  (:require [instaparse.core :as parse]
            [clojure.string :as s]))

(def parser (parse/parser "file:resources/door18.g"
                          :auto-whitespace :standard))

(defmulti interpret (fn [node] (first node)))

(defmethod interpret :expr [node]
  (if (= 4 (count node))
    ((interpret (nth node 2))
     (interpret (second node))
     (interpret (nth node 3)))
    (interpret (second node))
    )
  )

(defmethod interpret :term [node]
  (if (= "(" (second node))
    (interpret (nth node 2))
    (interpret (second node))
    )
  )

(defmethod interpret :op [node]
  (case (second node)
    "+" +
    "*" *
    )
  )

(defmethod interpret :val [node]
  (Integer/parseInt (second node))
  )

(defn interpret-one-line [line]
  (interpret (parser line))
  )

(defn read-lines [infn]
  (doseq [l (s/split (slurp infn) #"\n")]
    (println l)
    (println (interpret-one-line l)))
  )

(defn read-and-sum [infn]
  (reduce
    +
    (map
      interpret-one-line
      (s/split (slurp infn) #"\n")))
  )

