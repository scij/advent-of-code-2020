(ns advent-of-code-2020.door13
  (:require [clojure.string :as s]))

(defn find-next-bus [timestamp bus-ids]
  (let [max-ts (+ timestamp (apply max bus-ids))]
    (map (fn [x] {:line (:line x)
                  :dep  (first (:deps x))})
         (map (fn [step]
                {:line step
                 :deps (filter #(> % timestamp) (range 0 max-ts step))}
                )
              bus-ids))
    )
  )

(defn parse-input2 [str]
  (map
    #(Integer/parseInt %)
    (map
      #(s/replace % #"x" "1")
      (s/split "41,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,659,x,x,x,x,x,x,x,23,x,x,x,x,13,x,x,x,x,x,19,x,x,x,x,x,x,x,x,x,29,x,937,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17" #",")
      ))
  )

