(ns pronosmondial.routes.home
  (:use compojure.core)
  (:require [pronosmondial.layout :as layout]
            [pronosmondial.util :as util]
            [pronosmondial.db.core :as db]))

(defn home-page []
  (layout/render "home.html"))

(defn matchs []
  (println (db/get-next-matchs))
  (layout/render "matchs.html"
                 {:next-matchs  (db/get-next-matchs)
                  :past-matchs  (db/get-past-matchs)}))

(defn match []
  (layout/render "match.html"))

(defn teams []
  (layout/render "teams.html"))

(defn team []
  (layout/render "team.html"))

(defn users []
  (layout/render "users.html"))

(defn user []
  (layout/render "user.html"))

(defn ranking []
  (layout/render "ranking.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/matchs" [] (matchs))
  (GET "/matchs/:id" [id] (match))
  (GET "/teams" [] (teams))
  (GET "/teams/:id" [] (team))
  (GET "/users" [] (users))
  (GET "/users/:id" [id] (user))
  (GET "/ranking" [] (ranking)))
