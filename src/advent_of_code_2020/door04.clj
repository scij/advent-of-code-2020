(ns advent-of-code-2020.door04
  (require [clojure.core.async
            :as a
            :refer [<!! >!! go go-loop]]))

(def passport-fields ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"])
(def optional-passport-field "cid")

(defn parse-passport [passport-lines]
  (vec
    (map vec
         (map rest
              (apply concat
                     (map #(re-seq #"(\w{3}):(\w+)" %) passport-lines)))))
  )

(defn valid-passport [passport]
  (and
    (<= 7 (count passport))
    (>= 8 (count passport))
    (= 7 (count
           (filter identity
                   (map #(contains? passport %) passport-fields)
                   )
           )
       )
    (and
      (= 8 (count passport))
      (contains? passport optional-passport-field))
    )
  )

(defn read-batch [infn line-chan]
  (with-open [in (clojure.java.io/reader infn)]
    (doseq [line (line-seq in)]
      (>!! line-chan line)
      (println line))
    )
  )

(defn break-groups [line-chan]
  (go-loop [one-passport []
            line (<! line-chan)]
           (if (= "" line)
             (do
               (valid-passport (parse-passport one-passport))
               (recur [] (<! line-chan)))
             (do
               (recur (conj one-passport line) (<! line-chan)))
             )
           )
  )