(ns pronosmondial.db.core
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [pronosmondial.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity teams)
(defentity matchs)
(defentity users)
(defentity pronos)

;; Teams
(defn get-team [id]
  (first (select teams
                 (where {:id id})
                 (limit 1))))

(defn create-team [team]
  (insert teams
          (values team)))

(defn all-teams []
  (select teams
           (order :name :ASC)))

;; Matchs
(defn get-match [id]
  (first (select matchs
                 (where {:id id})
                 (limit 1))))

(defn get-next-matchs []
  (map
   #(assoc %
      :first_team_name (get (get-team (get % :first_team)) :name)
      :second_team_name (get (get-team (get % :second_team)) :name))
       (select matchs
                 (where {:date [>= (sqlfn now)]})
                 (order :date :DESC))))

(defn get-past-matchs []
  (select matchs
                 (where {:date [< (sqlfn now)]})
                 (order :date :DESC)))


(defn get-all-matchs-of-team [teamId]
  (select matchs
          (where (or ({:first_team teamId}) ({:second_team teamId})))
          )
  )

(defn create-match [match]
  (insert matchs
          (values match)))

;; Users
(defn get-user [id]
  (first (select users
                 (where {:id id})
                 (limit 1))))

(defn create-user [user]
  (insert users
          (values user)))

;; Pronos
(defn get-prono [id]
  (first (select pronos
                 (where {:id id})
                 (limit 1))))

(defn create-prono [prono]
  (insert pronos
          (values prono)))
