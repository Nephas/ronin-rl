(ns client.core
  (:require [quil.core :as q :include-macros true]
            [client.connect :refer [connect-socket]]
            [client.input :refer [handle-key handle-move]]
            [client.routing :as r]
            [client.view.graphics :refer [SCREENSIZE load-graphics]]
            [client.view.layers :refer [update render init-layers]]
            [quil.middleware :as m]
            [quil.core :as q]))

(defn setup []
  (println "initialising game:" r/game-id "as" r/player-id)
  (q/image-mode :center)
  (q/ellipse-mode :corner)
  (q/rect-mode :corner)
  (q/color-mode :hsb 1.0)

  (connect-socket)
  (load-graphics)
  (init-layers)
  {:game {}})

(q/defsketch -main
             :title "Hnefatafl"
             :size [SCREENSIZE SCREENSIZE]
             :setup setup

             :host "canvas"

             :key-pressed handle-key
             :mouse-moved handle-move
             :update update
             :draw render

             :middleware [m/fun-mode]
             :features [:global-key-events])