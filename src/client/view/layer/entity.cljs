(ns client.view.layer.entity
  (:require [quil.core :as q]
            [client.view.graphics :refer [ANGLES graphics draw-tile]]))


(defn draw-entity [state id]
  (let [graphics-key (get-in state [:drawable id])
        facing (get-in state [:facing id])
        pos (get-in state [:positions id])]
    (draw-tile (get @graphics graphics-key) pos (get ANGLES facing))))

(defn render-entities! [state layer]
  (println "prerendering entities")
  (q/with-graphics layer
                   (q/background 0.0 0.0)
                   (doseq [id (:entities state)]
                     (draw-entity state id))))