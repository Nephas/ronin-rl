(ns client.view.layer.floor
  (:require [quil.core :as q]
            [client.view.graphics :refer [ANGLES graphics draw-tile]]
            [lib.map :refer [TILES]]))

(defn draw-floor-tile [pos]
  (let [i (mod (hash pos) 4)
        angle (nth (vals ANGLES) i)]
    (draw-tile (:tile @graphics) pos angle)))

(defn render-floor! [state layer]
  (println "prerendering floor")
  (q/with-graphics layer
                   (doseq [pos TILES]
                     (draw-floor-tile pos))))
