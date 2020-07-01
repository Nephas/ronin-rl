(ns client.view.layer.entity
  (:require [quil.core :as q]
            [client.view.graphics :refer [ANGLES graphics draw-tile]]))

(def SIZE {:tree [3 3]})

(defn draw-entity [state id]
  (let [graphics-key (get-in state [:drawable id])
        facing (get-in state [:facing id])
        pos (get-in state [:positions id])
        size (get SIZE graphics-key)]
    (draw-tile (get @graphics graphics-key) pos (get ANGLES facing) size)))

(defn render-entities! [state layer]
  (println "prerendering entities")
  (q/with-graphics layer
                   (q/background 0.0 0.0)
                   (doseq [id (:entities state)]
                     (draw-entity state id))))