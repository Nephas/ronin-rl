(ns server.state)

(def store (atom {}))

(defn serialize-game [id]
  (str (get @store id)))

(defn initialize-game [id]
  (swap! store assoc id {:tic       0

                         :players   (list 1 2)

                         :acting    1

                         :entities  (list 1 2 10 11 12 13)

                         :health    {1 [3 3] 2 [3 3]}

                         :actions   {1 [3 5] 2 [3 5]}

                         :stance    {1 :straight 2 :straight}

                         :facing    {1 :down 2 :up}

                         :drawable  {1  :player_1
                                     2  :player_2
                                     10 :water
                                     11 :water
                                     12 :water
                                     13 :water}

                         :positions {1  [2 2]
                                     2  [7 7]
                                     10 [3 3]
                                     11 [6 3]
                                     12 [3 6]
                                     13 [6 6]}

                         :blocking  (list 1 2 10 11 12 13)}))