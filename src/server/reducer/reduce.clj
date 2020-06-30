(ns server.reducer.reduce
  (:require [server.reducer.move :refer [move-entity turn-entity move]]
            [server.reducer.attack :refer [change-stance attack]]
            [server.reducer.validate :refer [validate]]
            [lib.resource :refer [expend replenish]]))

(defn rotate-actors [actor n-players]
  (let [next (inc actor)]
    (if (<= next n-players) next 1)))

(defn regenerate-actor [state amount]
  (let [acting (:acting state)]
    (update-in state [:actions acting] replenish amount)))

(defn end-turn [state]
  (let [n-players (count (:players state))]
    (-> state
        (update :acting rotate-actors n-players)
        (regenerate-actor 3))))

(defn end-tic [state pid]
  (let [maybe-end-turn (fn [state]
                         (if (<= (get-in state [:actions pid 0]) 0)
                           (end-turn state) state))]
    (-> state
        (update-in [:actions pid] expend 1)
        (update :tic inc)
        (maybe-end-turn))))

(defn acting? [state pid]
  (= pid (:acting state)))

(defn reduce [state action pid]
  (let [[primary secondary] action]
    (cond (not (acting? state pid))
          state

          (= :pass primary)
          (-> state
              (end-turn))

          (and (= :move primary) (validate state action pid))
          (-> state
              (move-entity pid secondary)
              (end-tic pid))

          (= :turn primary)
          (-> state
              (turn-entity pid secondary)
              (end-tic pid))

          (= :stance primary)
          (-> state
              (change-stance pid secondary)
              (end-tic pid))

          (= :attack primary)
          (-> state
              (attack pid)
              (end-tic pid))

          true state)))