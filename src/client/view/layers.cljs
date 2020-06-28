(ns client.view.layers
  (:require [quil.core :as q]
            [client.connect :refer [remote-state]]
            [client.view.graphics :refer [TILES SCREENSIZE SCREENCENTER graphics draw-tile tiles-loaded?]]))

(def layers (atom {}))

(defn init-layers []
  (doseq [layer [:floor :entities]]
    (let [gr (q/create-graphics SCREENSIZE SCREENSIZE)]
    (q/with-graphics gr
                     (q/image-mode :center)
                     (q/color-mode :hsb 1.0))
    (swap! layers assoc layer gr))))

(def ANGLES {:up 0 :down q/PI :left (* 3 q/HALF-PI) :right q/HALF-PI})

(defn draw-floor-tile [pos]
  (let [i (mod (hash pos) 4)
        angle (nth (vals ANGLES) i)]
    (draw-tile (:tile @graphics) pos angle)))

(defn draw-entity [state id]
  (let [graphics-key (get-in state [:drawable id])
        facing (get-in state [:facing id])
        pos (get-in state [:positions id])]
    (draw-tile (get @graphics graphics-key) pos (get ANGLES facing))))

(defn render-floor! [state]
  (println "prerendering game board")
  (q/with-graphics (:floor @layers)
                   (doseq [pos TILES]
                     (draw-floor-tile pos))))

(defn render-entities! [state]
  (println "prerendering entities")
  (q/with-graphics (:entities @layers)
                   (q/background 0.0 0.0)
                   (doseq [id (:entities state)]
                     (draw-entity state id))))

(defn update [state]
  (let [new-state @remote-state]
    (if (and (some? new-state) (tiles-loaded?))
      (do (reset! remote-state nil)
          (render-floor! new-state)
          (render-entities! new-state)
          (assoc state :game new-state))
      state)))

(defn render [state]
  (when (and (some? (:floor @layers)) (some? (:entities @layers)))
    (q/image (:floor @layers) SCREENCENTER SCREENCENTER SCREENSIZE SCREENSIZE)
    (q/image (:entities @layers) SCREENCENTER SCREENCENTER SCREENSIZE SCREENSIZE)
    (let [highlight (:hovered state)]
      (when (some? highlight)
        (draw-tile (:cursor @graphics) highlight)))))