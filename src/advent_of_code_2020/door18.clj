(ns advent-of-code-2020.door18
  (:require [instaparse.core :as parse]))

(def parser (parse/parser
              "expr: #'\\d+' '+' expr |
                     #'\\d+' '*' expr |
                     '(' expr ')' |
                     #'\\d+'"
              :auto-whitespace :standard))

(defn evaluate-expr [expr]
  (println expr)
  (if (= "(" (second expr))
    (evaluate-expr (nth expr 2))
    (let [[_ num-s op expr2] expr
          num (Integer/parseInt num-s)]
      (case op
        "+" (+ num (evaluate-expr expr2))
        "*" (* num (evaluate-expr expr2))
        num
        )
      )
    )
  )



