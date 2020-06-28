(ns client.view.layers
  (:require [quil.core :as q]
            [client.connect :refer [remote-state]]
            [client.view.graphics :refer [TILES SCREENSIZE graphics draw-tile tiles-loaded?]]))

(def layers (atom {}))

(defn angle [facing]
  (get {:up 0 :down q/PI :left (* 3 q/HALF-PI) :right q/HALF-PI} facing))

(defn draw-entities [state]
  (q/no-tint)
  (doseq [id (:entities state)]
    (let [graphics-key (get-in state [:drawable id])
          facing (get-in state [:facing id])
          pos (get-in state [:positions id])]
      (draw-tile (get @graphics graphics-key) pos (angle facing)))))

(defn draw-board []
  (q/no-tint)
  (doseq [pos TILES]
    (let [i (mod (hash pos) 4)]
      (draw-tile (get-in @graphics [:tile i]) pos))))

(defn prerender-floor! [state]
  (when (and (nil? (:floor @layers)) (tiles-loaded?))
    (println "prerendering game board")
    (let [gr (q/create-graphics SCREENSIZE SCREENSIZE)]
      (q/with-graphics gr
                       (q/image-mode :center)
                       (q/color-mode :hsb 1.0)
                       (q/no-tint)
                       (draw-board))
      (swap! layers assoc :floor gr))))

(defn prerender-entities! [state]
  (println "prerendering entities")
  (let [gr (q/create-graphics SCREENSIZE SCREENSIZE)]
    (q/with-graphics gr
                     (q/image-mode :center)
                     (q/color-mode :hsb 1.0)
                     (q/no-tint)
                     (draw-entities state))
    (swap! layers assoc :entities gr)))

(defn fetch-remote! [state]
  (let [new-state @remote-state]
    (if (some? new-state)
      (do (reset! remote-state nil)
          (prerender-entities! new-state)
          (assoc state :game new-state))
      state)))

(defn update [state]
  (prerender-floor! state)
  (fetch-remote! state))

(defn render [state]
  (when (and (some? (:floor @layers)) (some? (:entities @layers)) (tiles-loaded?))
    (q/image (:floor @layers) 0 0 SCREENSIZE SCREENSIZE)
    (q/image (:entities @layers) 0 0 SCREENSIZE SCREENSIZE)
    (let [highlight (:hovered state)]
      (when (some? highlight)
        (draw-tile (:cursor @graphics) highlight)))))