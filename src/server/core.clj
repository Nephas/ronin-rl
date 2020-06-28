(ns server.core
  (:require [compojure.core :refer [context defroutes GET ANY]]
            [compojure.route :as route]
            [org.httpkit.server :as http]
            [environ.core :refer [env]]
            [clojure.java.io :as io]))

(def store (atom {}))

(def channels (atom {}))

(defroutes routes
           (route/resources "/")
           (route/files "/img")
           (route/files "/font")
           (GET "/" [] (slurp (io/resource "index.html"))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (println (str "http://localhost:" port))
    (println "https://ronin-rl.herokuapp.com")
    (http/run-server routes {:port port})))
