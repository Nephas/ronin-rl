(ns lib.map)

(def MAPSIZE 10)

(def TILES (apply concat (map (fn [x] (map (fn [y] [x y])
                                           (range MAPSIZE))) (range MAPSIZE))))