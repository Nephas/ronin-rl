(ns client.view.graphics
  (:require [quil.core :as q]
            [client.routing :as r]))

(def SCREENSIZE 600)
(def TILESIZE (/ SCREENSIZE 11.0))
(def TILES (apply concat (map (fn [x] (map (fn [y] [x y])
                                           (range 11))) (range 11))))

(def graphics (atom {}))

(defn fetch-img [id]
  (q/request-image (str r/origin "/img/" id ".png")))

(defn load-graphics []
  (println "fetching images")
  (swap! graphics #(-> % (assoc :tile [(fetch-img "tile_0")
                                       (fetch-img "tile_1")
                                       (fetch-img "tile_2")
                                       (fetch-img "tile_3")])
                       (assoc :player_1 (fetch-img "player_1"))
                       (assoc :player_2 (fetch-img "player_2"))
                       (assoc :cursor (fetch-img "cursor"))
                       (assoc :logo (fetch-img "clojure")))))

(defn tiles-loaded? []
  (and (= 4 (count (:tile @graphics)))
       (not (zero? (.-width (:cursor @graphics))))
       (not (zero? (.-width (:player_1 @graphics))))
       (not (zero? (.-width (:player_2 @graphics))))
       (not (zero? (.-width (get-in @graphics [:tile 0]))))
       (not (zero? (.-width (get-in @graphics [:tile 1]))))
       (not (zero? (.-width (get-in @graphics [:tile 2]))))
       (not (zero? (.-width (get-in @graphics [:tile 3]))))))

(defn draw-tile
  ([img [x y] rot]
   (let [x (+ (* 0.5 TILESIZE) (* TILESIZE x))
         y (+ (* 0.5 TILESIZE) (* TILESIZE y))]
     (q/with-translation [x y]
                         (q/with-rotation [rot]
                                          (q/image img 0 0 TILESIZE TILESIZE)))))
  ([img [x y]] (draw-tile img [x y] 0)))
