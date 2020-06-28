(ns client.connect
  (:require
    [cljs.reader :as reader]
    [client.routing :as r]))


(def remote-state (atom nil))

(def socket (atom nil))

(defn send [msg]
  (.send @socket msg))

(defn connect-socket []
  (let [protocol (if r/secure? "wss:" "ws:")
        channel (str protocol "//" r/host "/game/" r/game-id "/player/" r/player-id "/ws")]
    (println "connecting to socket" channel)
    (reset! socket (js/WebSocket. channel))

    (set! (.-onopen @socket)
          (fn [] (send (str "connect"))))

    (set! (.-onerror @socket)
          (fn [] (js/alert "error")))

    (set! (.-onmessage @socket)
          (fn [event]
            (let [data (reader/read-string (.-data event))]
              (cond (map? data) (reset! remote-state data)
                    (nil? data) (send "reset")
                    true (println "string:" data)))))))
