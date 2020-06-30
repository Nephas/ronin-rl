(ns client.view.layer.floor
  (:require [quil.core :as q]
            [client.view.graphics :refer [TILES SCREENSIZE SCREENCENTER ANGLES graphics draw-tile]]))

(defn draw-floor-tile [pos]
  (let [i (mod (hash pos) 4)
        angle (nth (vals ANGLES) i)]
    (draw-tile (:tile @graphics) pos angle)))

(defn render-floor! [state layer]
  (println "prerendering floor")
  (q/with-graphics layer
                   (doseq [pos TILES]
                     (draw-floor-tile pos))))
