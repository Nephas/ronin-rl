(ns server.reducer.reduce
  (:require [server.state :as s]))

(defn move [[x y] dir]
  (cond (= :up dir) [x (dec y)]
        (= :down dir) [x (inc y)]
        (= :left dir) [(dec x) y]
        (= :right dir) [(inc x) y]))

(defn turn [facing dir]
  (cond (= :clock dir) :up
        (= :counterclock dir) :down))

(defn move-entity [state id dir]
  (update-in state [:positions id] move dir))

(defn turn-entity [state id dir]
  (update-in state [:facing id] turn dir))

(defn reduce [state action pid]
  (let [[primary secondary] action]
    (cond (= :move primary) (move-entity state pid secondary)
          (= :turn primary) (turn-entity state pid secondary))))