(ns merida.dev-system
  (:require [com.stuartsierra.component :as component]
            [reloaded.repl :refer [init start stop go reset]]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [merida.pedestal :as pedestal]
            [merida.routes :as routes]))


(defn dev-system
  [env]
  (component/system-map
    :service-map
      {:env env

        ::http/routes #(route/expand-routes (deref #'routes/routes))
        ::http/resource-path "/public"
        ::http/type :jetty
        ::http/port 4000
        ::http/container-options {:h2c? true
                                  :h2? false
                                  :ssl? false}
        ::http/join?  false
        ::http/allowed-origins {:creds true :allowed-origins (constantly true)}
        ::http/secure-headers {:content-security-policy-settings {:object-src "none"}}
      }

    :pedestal
      (component/using
        (pedestal/new-pedestal)
          [:service-map])))


(reloaded.repl/set-init! #(dev-system :dev))
