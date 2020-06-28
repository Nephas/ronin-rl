(ns server.socket
  (:require [clojure.string :as str]
            [server.state :as s]
            [server.reducer.reduce :as r]
            [org.httpkit.server :as http]))

(def channels (atom {}))

(defn broadcast-at [gid msg]
  (doseq [other (get @channels gid)]
    (http/send! other msg)))

(defn handle [msg channel gid pid]
  (cond (str/includes? msg "connect")
        (do (swap! channels update gid conj channel)
            (http/send! channel (s/serialize-game gid))
            (broadcast-at gid (str "connect player " pid)))

        (str/includes? msg "reset")
        (do (s/initialize-game gid)
            (broadcast-at gid (s/serialize-game gid)))

        (str/includes? msg "action")
        (let [action (read-string (last (str/split msg #"action")))]
          (do (swap! s/store update gid r/reduce action pid)
              (broadcast-at gid (s/serialize-game gid))))))