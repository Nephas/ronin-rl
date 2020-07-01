(ns client.view.layer.weapon
  (:require [quil.core :as q]
            [lib.template :refer [project-template]]
            [client.view.graphics :refer [ANGLES graphics draw-tile draw-item]]))

(def STANCEANGLES {:left (- (* 0.2 q/PI)) :right (+ (* 0.2 q/PI)) :straight 0})

(def OFFSETS {:katana -1 :yari -1.5})
(def LENGTHS {:katana 3 :yari 4})

(defn draw-weapon [state id]
  (let [weapon (get-in state [:weapon id])
        facing (get-in state [:facing id])
        stance (get-in state [:stance id])
        angle (+ (get ANGLES facing) (get STANCEANGLES stance))
        pos (get-in state [:positions id])]
    (q/no-tint)
    (draw-item (get @graphics weapon) pos angle (get LENGTHS weapon) (get OFFSETS weapon))))

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

(defn render-weapons! [state layer]
  (println "prerendering entities")
  (q/with-graphics layer
                   (q/background 0.0 0.0)
                   (doseq [attacker (keys (:stance state))]
                     (draw-template state attacker))
                   (doseq [id (:players state)]
                     (draw-weapon state id))))