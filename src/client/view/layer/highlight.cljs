(ns client.view.layer.highlight
  (:require [quil.core :as q]
            [client.view.graphics :refer [TILES SCREENSIZE SCREENCENTER ANGLES graphics draw-tile]]
            [lib.template :refer [TEMPLATE add rotate-template project-template]]))

(defn draw-template [state id]
  (let [pos (get-in state [:positions id])
        facing (get-in state [:facing id])
        stance (get-in state [:stance id])]
    (q/tint 0.0 0.4 0.8)
    (doseq [highlight (project-template pos (:base TEMPLATE) facing)]
      (draw-tile (:kata_3 @graphics) highlight))
    (q/tint 0.0 0.2 1.0)
    (doseq [highlight (project-template pos (get TEMPLATE stance) facing)]
      (draw-tile (:kata_1 @graphics) highlight))))

(defn render-highlights! [state layer]
  (println "prerendering highlights")
  (q/with-graphics layer
                   (q/background 0.0 0.0)
                   (doseq [attacker (keys (:stance state))]
                     (draw-template state attacker))))
