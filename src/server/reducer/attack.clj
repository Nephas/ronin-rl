(ns server.reducer.attack
  (:require [clojure.set :refer [map-invert]]
            [lib.template :refer [TEMPLATE add rotate-template project-template]]
            [lib.resource :refer [expend replenish]]))

(defn change-stance [state id stance]
  (assoc-in state [:stance id] stance))

(defn damage [state pos amount]
  (let [entity (get (map-invert (:positions state)) pos)]
    (if (some? entity) (update-in state [:health entity] expend amount)
                       state)))

(defn damage-area [state positions amount]
  (loop [remaining positions
         next-state state]
    (if (empty? remaining) next-state
                           (recur (rest remaining)
                                  (damage next-state (first remaining) amount)))))

(defn attack [state id]
  (let [pos (get-in state [:positions id])
        facing (get-in state [:facing id])
        stance (get-in state [:stance id])
        base-targets (project-template pos (:base TEMPLATE) facing)
        stance-targets (project-template pos (get TEMPLATE stance) facing)]
    (-> state
        (damage-area base-targets 1)
        (damage-area stance-targets 1))))