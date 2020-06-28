(ns server.state)

(def store (atom {}))

(defn serialize-game [id]
  (str (get @store id)))

(defn initialize-game [id]
  (swap! store assoc id {:entities  (list 1 2)
                         :drawable  {1 :player_1
                                     2 :player_2}
                         :positions {1 [2 2]
                                     2 [5 5]}
                         :facing    {1 :left
                                     2 :right}}))