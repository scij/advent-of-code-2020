(ns advent-of-code-2020.door06
  (:require [clojure.string :as s]))

(defn parse-file [infn]
  (map s/split-lines
       (s/split (slurp infn) #"\n\n"))
  )

(defn process-groups [infn]
  (reduce +
          (map count
               (map #(apply hash-set %)
                    (map #(apply concat %)
                         (map
                           #(map
                              (fn [param1] (s/split param1 #""))
                              %)
                           (parse-file infn))))))
  )

(defn chm-put [chm key]
  (if (contains? chm key)
    (assoc chm key (inc (get chm key)))
    (assoc chm key 1))
  )

(defn count-one-group [group]
  (count
    (filter
      #(= (count group) (second %))
      (reduce chm-put (hash-map) (apply concat group))))
  )

(defn process-groups2 [infn]
  (reduce +
          (map
            count-one-group
            (map
              #(map
                 (fn [p] (s/split p #""))
                 %)
              (parse-file infn))))
  )