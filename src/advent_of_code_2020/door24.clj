(ns advent-of-code-2020.door24)

(defn new-pos [{x :x y :y} move]
  (case move
    "se" {:x (dec x) :y (inc y)}
    "sw" {:x (dec x) :y (dec y)}
    "ne" {:x (inc x) :y (inc y)}
    "nw" {:x (inc x) :y (dec y)}
    "e" {:x x :y (+ y 2)}
    "w" {:x x :y (- y 2)}
    )
  )

(defn tokenize-line [line]
  (map first (re-seq #"(se|sw|ne|nw|e|w)" line)))

(defn calc-tile-pos [line]
  (reduce
    new-pos
    {:x 0 :y 0}
    (tokenize-line line))
  )

(defn flip [state]
  (if (= state 0) 1 0))

(defn flip-tile [tile-states pos]
  (assoc tile-states pos (flip (get tile-states pos 0)))
  )

(defn evaluate-instructions [fn]
  (reduce
    flip-tile
    (hash-map)
    (map calc-tile-pos
         (line-seq (clojure.java.io/reader fn)))
    )
  )

(defn process-lines [fn]
  (reduce
    +
    (vals
      (evaluate-instructions fn)
      )
    )
  )

(defn adjacent? [pos1 pos2]
  (let [x-dist (Math/abs (- (:x pos1) (:x pos2)))
        y-dist (Math/abs (- (:y pos1) (:y pos2)))]
    (and
      (or (= 0 x-dist) (= 1 x-dist))
      (or (= 0 y-dist) (= 1 y-dist)))
    )
  )

(defn neighbors [pos]
  (map
    #(new-pos pos %)
    ["se" "sw" "e" "w" "ne" "nw"]))

(defn flip-black-tile? [tiles pos]
  (let [black-neighors (reduce + (map #(get tiles % 0) (neighbors pos)))]
    (or (= 0 black-neighors) (< 2 black-neighors))
    )
  )

(defn flip-white-tile? [tiles pos]
  (let [black-neighbors (reduce + (map #(get tiles % 0) (neighbors pos)))]
    (and (= 2 black-neighbors) (= 0 (get tiles pos 0)))
    )
  )

(defn find-black-tiles-to-flip [tiles]
  (filter
    #(flip-black-tile? tiles %)
    (keys
      (filter
        (fn [[_ v]]
          (= v 1))
        tiles)))
  )

(defn find-white-tiles-to-flip [tiles]
  (filter
    #(flip-white-tile? tiles %)
    (set
      (flatten
        (map
          neighbors
          (keys
            (filter
              (fn [[_ v]]
                (= v 1))
              tiles))))))
  )

(defn one-turn-of-flips [tiles]
  (let [to-white (find-black-tiles-to-flip tiles)
        to-black (find-white-tiles-to-flip tiles)]
    (merge
      tiles
      (zipmap to-white (repeat (count to-white) 0))
      (zipmap to-black (repeat (count to-black) 1))
      )
    )
  )

(defn turn-tiles [tiles iterations]
  (loop [n iterations
         t tiles]
    (if (< 0 n)
      (recur (dec n) (one-turn-of-flips t))
      t)
    )
  )

