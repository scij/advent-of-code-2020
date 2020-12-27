(ns advent-of-code-2020.door22)

(defn combat [card1 card2]
  (if (< card1 card2)
    2
    1)
  )

(defn create-decks []
  )

(defn cards-on-table [card1 card2]
  (if (< card1 card2)
    [card2 card1]
    [card1 card2]
    )
  )

(defn play-game [deck1 deck2]
  (loop [d1 deck1
         d2 deck2]
    (println "d1" d1)
    (println "d2" d2)
    (let [card1 (first d1)
          card2 (first d2)]
      (cond
        (empty? d1) d2
        (empty? d2) d1
        :else
        (if (= 1 (combat card1 card2))
          (recur
            (concat (rest d1) (cards-on-table card1 card2))
            (rest d2))
          (recur
            (rest d1)
            (concat (rest d2) (cards-on-table card1 card2)))
          )
        )
      )
    )
  )

(defn play-recursive-game [deck1 deck2]
  (println ">> Game" deck1 deck2)
  (loop [d1 deck1
         d2 deck2
         decks1 #{}
         decks2 #{}]
    (println "#######################")
    (println "d1" d1)
    (println "d2" d2)
    (println "decks1" decks1)
    (println "decks2" decks2)
    (let [card1 (first d1)
          card2 (first d2)]
      (cond
        (or
          (contains? decks1 d1)
          (contains? decks2 d2))
        d1
        (empty? d1) d2
        (empty? d2) d1
        (or
          (= card1 (dec (count d1)))
          (= card1 (dec (count d2)))
          (= card2 (dec (count d2)))
          (= card2 (dec (count d1))))
        (play-recursive-game (rest d1) (rest d2))
        :else
        (if (= 1 (combat card1 card2))
          (recur
            (concat (rest d1) (cards-on-table card1 card2))
            (rest d2)
            (conj decks1 d1)
            (conj decks2 d2))
          (recur
            (rest d1)
            (concat (rest d2) (cards-on-table card1 card2))
            (conj decks1 d1)
            (conj decks2 d2))
          )
        )
      )
    )
  )

(defn calc-score [winning-deck]
  (reduce
    (fn [{ind :ind acc :acc} card]
      (println ind acc card)
      {:ind (inc ind)
       :acc (+ acc (* ind card))})
    {:ind 1, :acc 0}
    (reverse winning-deck))
  )

