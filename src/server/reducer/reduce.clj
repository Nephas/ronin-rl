(ns server.reducer.reduce
  (:require [server.reducer.move :refer [move-entity turn-entity move]]
            [server.reducer.validate :refer [validate]]))


(defn execute [state action pid]
  (let [[primary secondary] action]
    (cond (= :move primary) (move-entity state pid secondary)
          (= :turn primary) (turn-entity state pid secondary))))

(defn reduce [state action pid]
  (if (validate state action pid)
    (do (println "\t * accepted action")
        (-> state
            (execute action pid)
            (update :tic inc)))
    (do (println "\t * rejected action")
        state)))