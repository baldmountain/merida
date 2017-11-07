(ns merida.system-test
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :refer [response-for]]
            [com.stuartsierra.component :as component]
            [clojure.test :refer :all]
            [merida.routes :as routes]
            [merida.system :as system]
            [merida.pedestal :as pedestal]))

(def url-for (route/url-for-routes
              (route/expand-routes routes/routes)))

(println (str (into [] (route/expand-routes routes/routes))))

(defn service-fn
  [system]
  (get-in system [:pedestal :service ::http/service-fn]))

(defmacro with-system
  [[bound-var binding-expr] & body]
  `(let [~bound-var (component/start ~binding-expr)]
     (try
       ~@body
       (finally
         (component/stop ~bound-var)))))

(deftest greeting-test
  (with-system [sut (system/system :test)]
    (let [service               (service-fn sut)
          {:keys [status body]} (response-for service :get (url-for :merida.routes/healthcheck))]
      (is (= 200 status))
      (is (= "ok" body)))))

(deftest home-page-test
  (with-system [sut (system/system :test)]
    (let [service               (service-fn sut)
          {:keys [status body headers]} (response-for service :get "/")]
      (is (= body "Hello World!"))
      (is (=
          headers
          {"Content-Type" "text/html;charset=UTF-8"
            "Strict-Transport-Security" "max-age=31536000; includeSubdomains"
            "X-Frame-Options" "DENY"
            "X-Content-Type-Options" "nosniff"
            "X-XSS-Protection" "1; mode=block"
            "X-Download-Options" "noopen"
            "X-Permitted-Cross-Domain-Policies" "none"
            "Content-Security-Policy" "object-src 'none'; script-src 'unsafe-inline' 'unsafe-eval' 'strict-dynamic' https: http:;"})))))
