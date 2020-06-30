(ns lib.resource)

(defn expend [[current limit] amount]
  [(max (- current amount) 0) limit])

(defn replenish [[current limit] amount]
  [(min (+ current amount) limit) limit])

