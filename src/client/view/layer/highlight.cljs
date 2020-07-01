(ns client.view.layer.highlight
  (:require [quil.core :as q]
            [client.view.graphics :refer [graphics draw-tile]]
            [lib.template :refer [project-template]]))

(defn draw-template [state id]
  (let [pos (get-in state [:positions id])
        facing (get-in state [:facing id])
        stance (get-in state [:stance id])
        weapon (get-in state [:weapon id])]
    (q/tint 0.0 0.4 1.0)
    (doseq [highlight (project-template pos weapon :base facing)]
      (draw-tile (:kata_0 @graphics) highlight))
    (q/tint 0.0 0.2 1.0)
    (doseq [highlight (project-template pos weapon stance facing)]
      (draw-tile (:kata_1 @graphics) highlight))))

(defn draw-actor-highlight [state]
  (let [actor (:acting state)
        pos (get-in state [:positions actor])]
    (q/tint 0.2 0.3 1.0)
    (draw-tile (:cursor @graphics) pos)))

(defn render-highlights! [state layer]
  (println "prerendering highlights")
  (q/with-graphics layer
                   (q/background 0.0 0.0)
                   (draw-actor-highlight state)
                   (doseq [attacker (keys (:stance state))]
                     (draw-template state attacker))))
