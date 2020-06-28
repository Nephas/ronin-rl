(ns server.state)

(def store (atom {}))

(defn serialize-game [id]
  (str (get @store id)))

(defn initialize-game [id]
  (swap! store assoc id {:tic       0
                         :entities  (list 1 2 10 11 12 13)
                         :drawable  {1  :player_1
                                     2  :player_2
                                     10 :water
                                     11 :water
                                     12 :water
                                     13 :water}
                         :positions {1  [2 2]
                                     2  [7 7]
                                     10 [4 4]
                                     11 [5 4]
                                     12 [4 5]
                                     13 [5 5]}
                         :blocking  (list 1 2 10 11 12 13)
                         :facing    {1 :down
                                     2 :up}}))