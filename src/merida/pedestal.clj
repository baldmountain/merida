(ns merida.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [merida.routes :as routes]))

(defn test?
  [service-map]
  (= :test (:env service-map)))

(defn dev?
  [service-map]
  (= :dev (:env service-map)))

(defrecord Pedestal [service-map
                     service]
  component/Lifecycle
  (start [this]
    (if service
      this
      (cond-> service-map
        (dev? service-map) http/default-interceptors
        (dev? service-map) http/dev-interceptors
        true                      http/create-server
        (not (test? service-map)) http/start
        true                      ((partial assoc this :service)))))
  (stop [this]
    (when (and service (not (test? service-map)))
      (http/stop service))
    (assoc this :service nil)))

(defn new-pedestal
  []
  (map->Pedestal {}))