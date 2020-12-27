(ns advent-of-code-2020.door10)

(defn prepare-data [infn]
  (with-open [in (clojure.java.io/reader infn)]
    (let [adapters (map #(Integer/parseInt %) (line-seq in))
          dev-in (+ 3 (reduce max adapters))]
      (conj adapters 0 dev-in)
      )
    )
  )

(defn count-diffs [adapters]
  (frequencies
    (map
      #(- (second %) (first %))
      (partition 2 1 (sort adapters))
      )
    )
  )

(defn count-permutations [adapters]
  (map count
   (filter
     #(= 1 (first %))
     (partition-by
       identity
       (map
         #(- (second %) (first %))
         (partition 2 1 (sort adapters))
         )
       )))
  )