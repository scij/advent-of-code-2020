(ns advent-of-code-2020.door12
  (:require [clojure.string :as s]))

(defn init-state []
  {:x    0
   :y    0
   :wp-x 10
   :wp-y 1
   :dir  0})

(defn new-dir [state op val]
  (let [result (op (:dir state) val)]
    (cond
      (neg? result) (+ 360 result)
      (>= result 360) (- result 360)
      :else result)
    )
  )

(defn go-north [val state]
  (assoc state :y (+ val (:y state)))
  )

(defn go-south [val state]
  (assoc state :y (- (:y state) val))
  )

(defn go-east [val state]
  (assoc state :x (+ val (:x state)))
  )

(defn go-west [val state]
  (assoc state :x (- (:x state) val))
  )

(defn turn-left [val state]
  (assoc state :dir (new-dir state - val))
  )

(defn turn-right [val state]
  (assoc state :dir (new-dir state + val))
  )

(defn forward [val state]
  (case (:dir state)
    0 (assoc state :x (+ (:x state) val))
    90 (assoc state :y (- (:y state) val))
    180 (assoc state :x (- (:x state) val))
    270 (assoc state :y (+ (:y state) val))
    )
  )

(defn steer [state instruction]
  (let [[_ dir val] (re-matches #"([NSEWLRF])(\d+)" instruction)]
    (case dir
      "N" (go-north (Integer/parseInt val) state)
      "S" (go-south (Integer/parseInt val) state)
      "E" (go-east (Integer/parseInt val) state)
      "W" (go-west (Integer/parseInt val) state)
      "L" (turn-left (Integer/parseInt val) state)
      "R" (turn-right (Integer/parseInt val) state)
      "F" (forward (Integer/parseInt val) state)
      )
    )
  )

(defn move-wp-north [val state]
  (assoc state :wp-y (+ (:wp-y state) val))
  )

(defn move-wp-south [val state]
  (assoc state :wp-y (- (:wp-y state) val))
  )

(defn move-wp-east [val state]
  (assoc state :wp-x (+ (:wp-x state) val))
  )

(defn move-wp-west [val state]
  (assoc state :wp-x (- (:wp-x state) val))
  )

(defn wp-ship-dist [state]
  {
   :x (- (:wp-x state) (:x state))
   :y (- (:wp-y state) (:y state))
   }
  )

(defn left-by [val state]
  (new-dir state - val)
  )

(defn right-by [val state]
  (new-dir state + val)
  )

;; (-(b-y)+x, (a-x)+y)
(defn rotate-wp [val state]
  (let [vector (wp-ship-dist state)]
    (case val
      ;; -y, x
      90 (assoc state
           :wp-x (:wp-y state)
           :wp-y (- 0 (:wp-x state))
           )
      ;; -x, -y
      180 (assoc state
            :wp-x (- 0 (:wp-x state))
            :wp-y (- 0 (:wp-y state))
            )
      ;; y, -x
      270 (assoc state
            :wp-x (- 0 (:wp-y state))
            :wp-y (:wp-x state)
            )
      )
    )
  )


(defn move-to-wp [val state]
  (assoc state
    :x (+ (:x state) (* (:wp-x state) val))
    :y (+ (:y state) (* (:wp-y state) val))
    )
  )

(defn move-wp [state instruction]
  (let [[_ dir val] (re-matches #"([NSEWLRF])(\d+)" instruction)]
    (case dir
      "N" (move-wp-north (Integer/parseInt val) state)
      "S" (move-wp-south (Integer/parseInt val) state)
      "E" (move-wp-east (Integer/parseInt val) state)
      "W" (move-wp-west (Integer/parseInt val) state)
      "L" (rotate-wp (left-by (Integer/parseInt val) state) state)
      "R" (rotate-wp (right-by (Integer/parseInt val) state) state)
      "F" (move-to-wp (Integer/parseInt val) state)
      )
    )
  )

(defn process-input [instructions state]
  (if (empty? instructions)
    state
    (recur (rest instructions) (steer state (first instructions))))
  )

(defn parse-lines [fn]
  (let [final-pos (process-input (s/split (slurp fn) #"\n") (init-state))]
    (+ (Math/abs (:x final-pos))
       (Math/abs (:y final-pos))))
  )

(defn process-input-2 [instructions state]
  (println (first instructions) state)
  (if (empty? instructions)
    state
    (recur (rest instructions) (move-wp state (first instructions))))
  )

(defn parse-lines-2 [fn]
  (let [final-pos (process-input-2 (s/split (slurp fn) #"\n") (init-state))]
    (+ (Math/abs (:x final-pos))
       (Math/abs (:y final-pos))))
  )