(ns client.view.graphics
  (:require [quil.core :as q]
            [client.routing :as r]
            [lib.map :refer [TILES]]))

(def SCREENSIZE 500)
(def SCREENCENTER (* 0.5 SCREENSIZE))
(def TILESIZE (/ SCREENSIZE 10.0))


(def ANGLES {:up 0 :down q/PI :left (* 3 q/HALF-PI) :right q/HALF-PI})

(def graphics (atom {}))

(defn fetch-img [id]
  (q/request-image (str r/origin "/img/" id ".png")))



(defn load-graphics []
  (println "fetching images")
  (reset! graphics {:tile     (fetch-img "tile")
                    :player_1 (fetch-img "player_1")
                    :player_2 (fetch-img "player_2")
                    :katana (fetch-img "katana")
                    :kata_0   (fetch-img "kata_0")
                    :kata_1   (fetch-img "kata_1")
                    :water    (fetch-img "water")
                    :cursor   (fetch-img "cursor")
                    :logo     (fetch-img "clojure")}))

(defn tiles-loaded? []
  (let [loaded? #(not (zero? (.-width (% @graphics))))]
    (reduce #(and %1 %2) (map #(loaded? % @graphics) (keys @graphics)))))

(defn draw-tile
  ([img [x y] rot]
   (let [x (+ (* 0.5 TILESIZE) (* TILESIZE x))
         y (+ (* 0.5 TILESIZE) (* TILESIZE y))]
     (q/with-translation [x y]
                         (q/with-rotation [rot]
                                          (q/image img 0 0 TILESIZE TILESIZE)))))
  ([img [x y]] (draw-tile img [x y] 0)))
