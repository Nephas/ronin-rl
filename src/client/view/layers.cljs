(ns client.view.layers
  (:require [quil.core :as q]
            [client.connect :refer [remote-state]]
            [client.view.layer.floor :refer [render-floor!]]
            [client.view.layer.entity :refer [render-entities!]]
            [client.view.layer.gui :refer [render-gui!]]
            [client.view.layer.highlight :refer [render-highlights!]]
            [client.view.layer.weapon :refer [render-weapons!]]
            [client.view.graphics :refer [SCREENSIZE SCREENCENTER ANGLES graphics draw-tile tiles-loaded?]]))

(def LAYERS [:floor :weapons :entities :gui :highlight])

(def layers (atom {}))

(defn init-layers []
  (doseq [layer LAYERS]
    (let [gr (q/create-graphics SCREENSIZE SCREENSIZE)]
      (q/with-graphics gr
                       (q/image-mode :center)
                       (q/color-mode :hsb 1.0))
      (swap! layers assoc layer gr))))

(defn update [state]
  (let [new-state @remote-state]
    (if (and (some? new-state) (tiles-loaded?))
      (do (reset! remote-state nil)
          (render-floor! new-state (:floor @layers))
          (render-weapons! new-state (:weapons @layers))
          (render-entities! new-state (:entities @layers))
          (render-highlights! new-state (:highlight @layers))
          (render-gui! new-state (:gui @layers))
          (assoc state :game new-state))
      state)))

(defn render-layer [layer]
  (q/image (get @layers layer) SCREENCENTER SCREENCENTER SCREENSIZE SCREENSIZE))

(defn render [state]
  (when (and (some? (:floor @layers)) (some? (:entities @layers)))
    (doseq [layer LAYERS]
      (render-layer layer))
    (let [highlight (:hovered state)]
      (when (some? highlight)
        (draw-tile (:cursor @graphics) highlight)))))