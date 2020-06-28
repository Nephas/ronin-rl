(ns client.view.graphics
  (:require [quil.core :as q]
            [client.routing :as r]))

(def SCREENSIZE 600)
(def SCREENCENTER (* 0.5 SCREENSIZE))
(def TILESIZE (/ SCREENSIZE 10.0))
(def TILES (apply concat (map (fn [x] (map (fn [y] [x y])
                                           (range 10))) (range 10))))

(def graphics (atom {}))

(defn fetch-img [id]
  (q/request-image (str r/origin "/img/" id ".png")))

(defn load-graphics []
  (println "fetching images")
  (swap! graphics #(-> % (assoc :tile (fetch-img "tile"))
                       (assoc :player_1 (fetch-img "player_1"))
                       (assoc :player_2 (fetch-img "player_2"))
                       (assoc :water (fetch-img "water"))
                       (assoc :cursor (fetch-img "cursor"))
                       (assoc :logo (fetch-img "clojure")))))

(defn tiles-loaded? []
  (and
       (not (zero? (.-width (:cursor @graphics))))
       (not (zero? (.-width (:player_1 @graphics))))
       (not (zero? (.-width (:player_2 @graphics))))
       (not (zero? (.-width (:tile @graphics))))
       (not (zero? (.-width (:water @graphics))))))

(defn draw-tile
  ([img [x y] rot]
   (let [x (+ (* 0.5 TILESIZE) (* TILESIZE x))
         y (+ (* 0.5 TILESIZE) (* TILESIZE y))]
     (q/with-translation [x y]
                         (q/with-rotation [rot]
                                          (q/image img 0 0 TILESIZE TILESIZE)))))
  ([img [x y]] (draw-tile img [x y] 0)))
