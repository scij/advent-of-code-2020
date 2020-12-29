(ns advent-of-code-2020.door04
  (:require [clojure.string :as s]))

(def passport-fields ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"])
(def optional-passport-field "cid")

(defn has-keys? [m ks]
  (apply = (map count [ks (select-keys m ks)]))
  )

(defn between? [lwb val upb]
  (and
    (<= lwb val)
    (>= upb val)
    )
  )

(defn valid-year? [passport name lwb upb]
  (and
    (re-matches #"\d{4}" (get passport name))
    (between? lwb (Integer/parseInt (get passport name)) upb))
  )

(defn parse-passport [passport]
  (reduce
    #(assoc %1 (second %2) (nth %2 2))
    (hash-map)
    (re-seq #"(\w{3}):(#?\w+)" passport))
  )

(defn valid-height [passport]
  (let [[_ hgt unit] (re-matches #"(\d{2,3})(cm|in)" (get passport "hgt"))]
    (or
      (and
        (= "cm" unit)
        (between? 150 (Integer/parseInt hgt) 193))
      (and
        (= "in" unit)
        (between? 59 (Integer/parseInt hgt) 76)))
    )
  )

(defn valid-hair-color [passport]
  (re-matches #"#[0-9a-f]{6}" (get passport "hcl"))
  )

(defn valid-eye-color [passport]
  (re-matches #"(amb|blu|brn|gry|grn|hzl|oth)" (get passport "ecl"))
  )

(defn valid-passport-id [passport]
  (re-matches #"\d{9}" (get passport "pid"))
  )

(defn valid-passport [passport]
  (and
    (has-keys? passport passport-fields)
    (valid-year? passport "byr" 1920 2002)
    (valid-year? passport "iyr" 2010 2020)
    (valid-year? passport "eyr" 2020 2030)
    (valid-height passport)
    (valid-hair-color passport)
    (valid-eye-color passport)
    (valid-passport-id passport)
    )
  )

(defn read-batch [infn]
  (s/split (slurp infn) #"\n\n")
  )

(defn validate-passports [infn]
  (count
    (filter
      identity
      (map
        valid-passport
        (map
          parse-passport
          (read-batch infn)))))
  )
