(ns advent-of-code-2020.door03)

(defn parse-line [line]
  (vec (map #(= \# %) (seq line)))
  )

(defn read-pattern [fn]
  (vec
    (map parse-line
        (with-open [in (clojure.java.io/reader fn)]
          (doall (line-seq in))
          )
        )
    )
  )

(defn tree? [x y forest]
  (let [width (count (forest 0))]
    (get (forest y) (mod x width))
    )
  )

(defn walk-forest [down right forest]
  (let [vec-x (vec (range 0 (* right (count forest)) right))
        vec-y (vec (range 0 (count forest) down))]
    (println vec-x vec-y)
    (count
     (filter identity
             (for [i (range 0 (count vec-y))]
               (do
                 (println (vec-x i) (vec-y i))
                 (tree? (vec-x i) (vec-y i) forest)))
             )
     ))
  )