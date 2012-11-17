(ns hello-cljs-ko.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [ring.middleware.reload :as reload]
            [hiccup.core :as h]
            [hiccup.page :as hp]))

(defn render-body []
  (hp/html5
     
     [:head]
     [:body
 
      [:p "First name: " [:strong {:data-bind "text: firstName"} "todo"]]
      [:p "Last name: " [:strong {:data-bind "text: lastName"} "todo"]]
      [:p "Full name: " [:strong {:data-bind "text: fullName"} "todo"]]
      
      [:p "First name: " [:input {:data-bind "value: firstName"}]]
      [:p "Last name: " [:input {:data-bind "value: lastName"}]]
      
      [:button {:data-bind "click: capitalizeLastName"} "Go caps"]
   
      (hp/include-js 
        "//ajax.aspnetcdn.com/ajax/knockout/knockout-2.1.0.js"
        "js/cljs.js")
      (hp/include-css "css/todo.css")
      ]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-body)})

(def app 
  (-> handler
    (resources/wrap-resource "public")
    (reload/wrap-reload)))

(defn -main [& args]
  (jetty/run-jetty app {:port 3000}))
