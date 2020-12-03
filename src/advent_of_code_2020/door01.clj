(ns advent-of-code-2020.door01)

(def numbers '(1721 979 366 299 675 1456))

(defn inner2 [n numbers]
  (if (= 2020 (+ n (first numbers)))
    (println (* n (first numbers)))
    (when (< 1 (count (rest numbers))) (recur n (rest numbers)))
    ))

(defn outer2 [numbers]
  (inner2 (first numbers) (rest numbers))
  (when (< 1 (count (rest numbers))) (recur (rest numbers))))

(defn inner3 [n1 n2 numbers]
  ;  (println "inner" n1 n2 numbers)
  (if (= 2020 (+ n1 n2 (first numbers)))
    (println (* n1 n2 (first numbers)))
    (when (< 1 (count (rest numbers))) (recur n1 n2 (rest numbers)))))

(defn middle [n1 numbers]
  ;(println "middle" n1 numbers)
  (inner3 n1 (first numbers) (rest numbers))
  (when (< 1 (count (rest numbers))) (recur n1 (rest numbers))))

(defn outer3 [numbers]
  ;(println "outer" numbers)
  (middle (first numbers)  (rest numbers))
  (when (< 1 (count (rest numbers))) (recur (rest numbers))))
