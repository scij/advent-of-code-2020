(ns advent-of-code-2020.door08
  (:require [clojure.string :as s]))

(defn execute-instruction-at [instructions pc acc visits]
  ;(println pc acc)
  (cond
    ; exit if an instruction is executed twice
    (contains? visits pc)
    (do
      (println "twice" acc)
      acc)
    ; exit if the end of the code is reached
    (= pc (count instructions))
    (do
      (println "exit" acc)
      acc)
    ; execute normally
    (< pc (count instructions))
    (do
      (let [[instr arg] (s/split (instructions pc) #" ")]
        (case instr
          "nop" (recur instructions (inc pc) acc (conj visits pc))
          "acc" (recur instructions (inc pc) (+ acc (Integer/parseInt arg)) (conj visits pc))
          "jmp" (recur instructions (+ pc (Integer/parseInt arg)) acc (conj visits pc))
          )
        )
      )
    ; otherwise
    :else
    (println "Hä?" pc acc)
    )
  )

(defn read-instr-file [infn]
  (with-open [in (clojure.java.io/reader infn)]
    (let [instr (vec (line-seq in))]
      (execute-instruction-at instr 0 0 #{})
      )
    )
  )

(defn read-instr-file-and-flip [infn]
  (with-open [in (clojure.java.io/reader infn)]
    (let [instr (vec (line-seq in))]
      (concat
        (for [flip-ind (keep-indexed #(when (s/starts-with? %2 "jmp") %1) instr)]
          (execute-instruction-at
            (assoc instr flip-ind (s/replace (instr flip-ind) "jmp" "nop"))
            0 0 #{})
          )
        (for [flip-ind (keep-indexed #(when (s/starts-with? %2 "nop") %1) instr)]
          (execute-instruction-at
            (assoc instr flip-ind (s/replace (instr flip-ind) "nop" "jmp"))
            0 0 #{})
          ))
      )
    )
  )