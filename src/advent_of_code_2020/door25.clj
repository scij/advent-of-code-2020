(ns advent-of-code-2020.door25)

(defn transform-step [val subject-nr]
  (rem (* val subject-nr) 20201227))

(defn transform-step [subject-nr loop-size]
  (for [val (range loop-size)])
  )
