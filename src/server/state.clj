(ns server.state
  (:require [quil.core :as q]
            [lib.map :refer [TILES DIRECTIONS rand-reset! rand-tile!]]))

(def store (atom {}))

(defn serialize-game [id]
  (str (get @store id)))

(defn initialize-game [id]
  (rand-reset! #{[2 2] [7 7]})
  (swap! store assoc id {:tic       0

                         :entities  (list 1 2 10 11 12 13 14 15 16 17)
                         :blocking  (list 1 2 10 11 12 13 14 15 16 17)

                         :players   (list 1 2)
                         :acting    1
                         :health    {1 [3 3] 2 [3 3]}
                         :actions   {1 [3 5] 2 [3 5]}
                         :stance    {1 :straight 2 :straight}
                         :weapon    {1 :katana 2 :yari}

                         :facing    {1  :down
                                     2  :up
                                     10 (rand-nth DIRECTIONS)
                                     11 (rand-nth DIRECTIONS)
                                     12 (rand-nth DIRECTIONS)
                                     13 (rand-nth DIRECTIONS)
                                     14 (rand-nth DIRECTIONS)
                                     15 (rand-nth DIRECTIONS)
                                     16 (rand-nth DIRECTIONS)
                                     17 (rand-nth DIRECTIONS)
                                     }

                         :drawable  {1  :player_1
                                     2  :player_2
                                     10 :tree
                                     11 :tree
                                     12 :tree
                                     13 :column
                                     14 :column
                                     15 :column
                                     16 :column
                                     17 :column
                                     }

                         :positions {1  [2 2]
                                     2  [7 7]
                                     10 (rand-tile!)
                                     11 (rand-tile!)
                                     12 (rand-tile!)
                                     13 (rand-tile!)
                                     14 (rand-tile!)
                                     15 (rand-tile!)
                                     16 (rand-tile!)
                                     17 (rand-tile!)
                                     }
                         }))