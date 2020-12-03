(ns advent-of-code-2020.door02)

(defn parse-line [str]
  (rest (first (re-seq #"(\d+)-(\d+) (\w): (\w+)" str))))

(defn between [lwb upb val]
  (and (<= lwb val) (>= upb val)))

(defn match-policy1 [lwb upb char pwd]
  (between (Integer/parseInt lwb)
           (Integer/parseInt upb)
           (count (re-seq (re-pattern char) pwd))))

(defn match-policy2 [i1 i2 char pwd]
  (let [ind1 (Integer/parseInt i1)
        ind2 (Integer/parseInt i2)]
    (or
      (and
       (= char (subs pwd (dec ind1) ind1))
       (not (= char (subs pwd (dec ind2) ind2)))
       )
      (and
        (= char (subs pwd (dec ind2) ind2))
        (not (= char (subs pwd (dec ind1) ind1)))
        )
      )
    )
  )

(defn check-pwds [policy infile]
  (with-open [in (clojure.java.io/reader infile)]
    (binding [*in* in]
      (count
        (filter identity
                (for [line (line-seq in)]
                  (do
                    (println line)
                    (apply policy (parse-line line))
                    )
                   )
                )
        )
      )
    )
  )