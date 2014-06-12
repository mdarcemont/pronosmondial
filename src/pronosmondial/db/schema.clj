(ns pronosmondial.db.schema
  (:require [clojure.java.jdbc :as sql]
            [noir.io :as io])
  (:use clj-bonecp-url.core))

(def db-store "site.db")

;(def db-spec
 ; (datasource-from-url
  ;  (or (System/getenv "DATABASE_URL")
   ;     "postgres://ihbuyrnexixcpt:AQ0WZc1AxPwhco5qJXOfLB7EcX@ec2-54-221-243-6.compute-1.amazonaws.com:5432/d1chv12mpunado")))

(def db-spec  {:connection-uri (str "jdbc:postgresql://ec2-54-221-243-6.compute-1.amazonaws.com:5432/d1chv12mpunado?user=ihbuyrnexixcpt&password=AQ0WZc1AxPwhco5qJXOfLB7EcX&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory")})

;{:subprotocol "postgresql"
;              :subname "//ec2-54-221-243-6.compute-1.amazonaws.com/d1chv12mpunado"
;              :user "ihbuyrnexixcpt"
;              :password "AQ0WZc1AxPwhco5qJXOfLB7EcX"
;              :sslmode "require"})

(defn initialized?
  "checks to see if the database schema is present"
  []
  (.exists (new java.io.File (str (io/resource-path) db-store ".h2.db"))))

(defn create-team-table
  []
  (sql/db-do-commands
    db-spec
    (sql/create-table-ddl
      :teams
      [:id "int(10) PRIMARY KEY AUTO_INCREMENT"]
      [:name "varchar(30)"]
      [:eliminated "boolean"])))

(defn create-match-table
  []
  (sql/db-do-commands
    db-spec
    (sql/create-table-ddl
      :matchs
      [:id "int(10) PRIMARY KEY AUTO_INCREMENT"]
      [:first_team "references teams (id)"]
      [:second_team "references teams (id)"]
      [:date "date"]
      [:description "varchar(50)"])))

(defn create-user-table
  []
  (sql/db-do-commands
    db-spec
    (sql/create-table-ddl
      :users
      [:id "int(10) PRIMARY KEY AUTO_INCREMENT"]
      [:login "varchar(50)"]
      [:password "varchar(20)"]
      [:email "varchar(50)"]
      [:name "varchar(50)"])))

(defn create-prono-table
  []
  (sql/db-do-commands
    db-spec
    (sql/create-table-ddl
      :pronos
      [:id "int(10) PRIMARY KEY AUTO_INCREMENT"]
      [:match "references matchs (id)"]
      [:gambler "references users (id)"]
      [:winner "references teams (id)"]
      [:first_team_score "integer(1)"]
      [:second_team_score "integer(1)"])))


(defn create-tables
  "creates the database tables used by the application"
  []
  (create-team-table)
  (create-match-table)
  (create-user-table)
  (create-prono-table))
