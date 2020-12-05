(ns advent-of-code-2020.door05)

(defn select-column [row lwb upb seat-key]
  (let [new-interval (/ (- upb lwb) 2)]
    (case (first seat-key)
      \R (select-column row (+ lwb new-interval) upb (rest seat-key))
      \L (select-column row lwb (- upb new-interval) (rest seat-key))
      \X lwb
      )
    )
  )

(defn select-row [lwb upb seat-key]
  (let [new-interval (/ (- upb lwb) 2)]
    (case (first seat-key)
      \F (select-row lwb (- upb new-interval) (rest seat-key))
      \B (select-row (+ lwb new-interval) upb (rest seat-key))
      (\L \R) (+ (* lwb 8) (select-column lwb 0 8 seat-key))
      )
    )
  )

(defn find-max-in-files [infn]
  (with-open [in (clojure.java.io/reader infn)]
    (sort
           (for [line (line-seq in)]
             (select-row 0 128 (str line "X"))))
    )
  )

(defn find-one [infn]
  (with-open [in (clojure.java.io/reader infn)]
    (let [seatmap (sort
       (for [line (line-seq in)]
         (select-row 0 128 (str line "X"))))
          act-total (reduce + seatmap)
          exp-total (reduce + (range (first seatmap) (inc (last seatmap))))]
      (- exp-total act-total)
      )
    )
  )