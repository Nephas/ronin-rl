(ns server.reducer.move
  (:require [clojure.set :refer [map-invert]]))


(def turn-directions {0 :up
                      1 :right
                      2 :down
                      3 :left})

(defn turn [facing dir]
  (let [current (facing (map-invert turn-directions))]
    (cond (= :left dir) (get turn-directions (mod (dec current) 4))
          (= :right dir) (get turn-directions (mod (inc current) 4)))))

(defn move [[x y] dir]
  (cond (= :up dir) [x (dec y)]
        (= :down dir) [x (inc y)]
        (= :left dir) [(dec x) y]
        (= :right dir) [(inc x) y]))

(defn move-entity [state id dir]
  (update-in state [:positions id] move dir))

(defn turn-entity [state id dir]
  (update-in state [:facing id] turn dir))