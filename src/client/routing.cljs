(ns client.routing
  (:require [clojure.string :as s]
            [clojure.string :as str]))

(def host (. (. js/document -location) -host))
(def origin (. (. js/document -location) -origin))
(def path (. (. js/document -location) -pathname))

(def player-id (-> path
                 (str/split #"player/")
                 (second)
                 (str/split #"/")
                 (first)))

(def game-id (-> path
                 (str/split #"game/")
                 (second)
                 (str/split #"/")
                 (first)))

(def secure? (= "https:" (. (. js/document -location) -protocol)))
