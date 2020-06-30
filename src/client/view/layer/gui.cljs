(ns client.view.layer.gui
  (:require [quil.core :as q]
            [client.view.graphics :refer [TILES SCREENSIZE SCREENCENTER ANGLES graphics draw-tile]]))


(defn player-info [state pid [x y]]
  (let [[health max-health] (get-in state [:health pid])
        [actions max-actions] (get-in state [:actions pid])
        stance (get-in state [:stance pid])]
    (q/text (str "player " pid "\n"
                 " - health: " health "/" max-health "\n"
                 " - actions: " actions "/" max-actions "\n"
                 " - stance: " stance) x y)))

(defn render-gui! [state layer]
  (println "prerendering gui")
  (q/with-graphics layer
                   (q/text-size 18)
                   (q/background 0.0 0.0)

                   (q/text (str "acting: " (get-in state [:acting])) 10 30)

                   (player-info state 1 [10 60])
                   (player-info state 2 [10 150])))