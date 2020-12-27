(ns advent-of-code-2020.door19
  (:require [instaparse.core :as parse]))

(defn read-file [infn]
  (let [input (slurp infn)
        [syntax data] (clojure.string/split input #"\n\n")
        parser (parse/parser syntax)
        lines (clojure.string/split data #"\n")]
    (count
      (filter identity
              (map vector?
                   (map parser lines))))
    ))
