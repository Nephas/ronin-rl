(ns client.input
  (:require [quil.core :as q]
            [client.connect :refer [send]]
            [client.view.graphics :refer [TILESIZE]]
            ))

(def coded-keys {32 :space
                 8  :back
                 9  :tab
                 10 :enter})

(defn commit! [action]
  (println (str "action " action))
  (send (str "action " action)))

(defn reset! []
  (send "reset"))

(def actions {:q     [:turn :left]
              :e     [:turn :right]

              :w     [:move :up]
              :a     [:move :left]
              :s     [:move :down]
              :d     [:move :right]

              :1     [:stance :left]
              :2     [:stance :straight]
              :3     [:stance :right]

              :t     [:pass nil]
              :space [:attack nil]})


(defn handle-move [state event]
  (let [screenpos [(:x event) (:y event)]
        pos (mapv #(int (/ % TILESIZE)) screenpos)]
    (assoc state :hovered pos)))

(defn handle-key [state event]
  (let [coded-key (get coded-keys (q/key-code))
        key (if (some? coded-key) coded-key (:key event))]
    (do (println "keypress: " (q/key-code) " - " (:key event))
        (cond (= key :r) (reset!)
              (contains? actions key) (commit! (get actions key)))
        state)))