(ns lib.map
  (:require [quil.core :as q]))

(def MAPSIZE 10)
(def DIRECTIONS (list :up :down :left :right))

(def TILES (apply concat (map (fn [x] (map (fn [y] [x y])
                                           (range MAPSIZE))) (range MAPSIZE))))

(def rand-tiles (atom (shuffle TILES)))

(defn rand-reset! [excluded]
  (reset! rand-tiles (filter #(not (contains? excluded %))
                             (shuffle TILES))))

(defn rand-tile! []
  (let [tile (first @rand-tiles)]
    (swap! rand-tiles rest) tile))
