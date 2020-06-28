(ns server.core
  (:require [compojure.core :refer [context defroutes GET ANY]]
            [compojure.route :as route]
            [org.httpkit.server :as http]
            [environ.core :refer [env]]
            [server.socket :refer [handle]]
            [clojure.java.io :as io]))

(defn log-header [gid pid]
  (str "[gid: " gid " - pid: " pid "]"))

(defn ws-handler [gid pid]
  (fn [req]
    (http/with-channel req channel
                       (http/on-close channel (fn [status] (println (log-header gid pid) "disconnect channel")))
                       (http/on-receive channel (fn [msg] (do (println (log-header gid pid) msg)
                                                              (handle msg channel gid pid)))))))

(defroutes routes
           (route/resources "/")
           (route/files "/img")
           (route/files "/font")
           (GET "/" [] (slurp (io/resource "index.html")))
           (context "/game/:gid/player/:pid" [gid pid]
             (GET "/" [] (slurp (io/resource "index.html")))
             (GET "/ws" [] (ws-handler gid (Integer/parseInt pid)))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (println (str "http://localhost:" port "/game/0/player/1"))
    (println "https://ronin-rl.herokuapp.com")
    (http/run-server routes {:port port})))
