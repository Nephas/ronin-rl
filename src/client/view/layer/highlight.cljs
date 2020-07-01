(ns client.view.layer.highlight
  (:require [quil.core :as q]
            [client.view.graphics :refer [graphics draw-tile]]
            [lib.template :refer [project-template]]))

(defn draw-actor-highlight [state]
  (let [actor (:acting state)
        pos (get-in state [:positions actor])]
    (q/tint 0.2 0.3 1.0)
    (draw-tile (:cursor @graphics) pos)))

(defn render-highlights! [state layer]
  (println "prerendering highlights")
  (q/with-graphics layer
                   (q/background 0.0 0.0)
                   (draw-actor-highlight state)))
