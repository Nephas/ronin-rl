(ns client.input
  (:require [quil.core :as q]
            [client.connect :refer [send]]
            [client.view.graphics :refer [TILESIZE]]
            ))

(defn commit! [action]
  (send (str "action " action)))

(defn reset! []
  (send "reset"))

(def actions {:q [:turn :left]
              :e [:turn :right]

              :w [:move :up]
              :a [:move :left]
              :s [:move :down]
              :d [:move :right]

              :t [:pass nil]
              :c [:attack nil]})


(defn handle-move [state event]
  (let [screenpos [(:x event) (:y event)]
        pos (mapv #(int (/ % TILESIZE)) screenpos)]
    (assoc state :hovered pos)))

(defn handle-key [state event]
  (let [key (:key event)]
    (do (println "keypress: " (q/key-code) " - " (:key event))
        (cond (= key :r) (reset!)
              (contains? actions key) (commit! (get actions key)))
        state)))