(ns server.reducer.validate
  (:require [server.reducer.move :refer [move]]))


(defn blocked? [state pos]
  (let [blocked-pos (vals (select-keys (:positions state) (:blocking state)))]
    (some #(= pos %) blocked-pos)))

(defn validate [state action pid]
  (let [[primary secondary] action
        origin (get-in state [:positions pid])
        target (move origin secondary)]
    (cond (= :move primary) (not (blocked? state target))
          true true)))