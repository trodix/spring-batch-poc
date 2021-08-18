# Spring Batch POC

> Ce projet permet de découvrir le potentiel de Spring Batch

## Les Jobs

Liste des Jobs de l'application

- Todo Job (todos-migration-job)

## Dashboard

Un dashboard est développé en Angular.

Il permet de:

- Lancer un Job en passant des paramètres
- Afficher l'historique des Jobs executés

## Setup développement

Lancer le serveur `./mvnw spring-boot:run`

Lancer le serveur de dev de Angular `cd src/main/migration-dashboard && ng serve`

Dashboard <http://localhost:8001>

Liste des jobs <http://localhost:8001/api/migration/jobs/job-list>

Liste des instances de jobs <http://localhost:8001/api/migration/jobs/job-instances>

Liste des executions de jobs <http://localhost:8001/api/migration/jobs/job-executions>

Todo Job <http://localhost:8001/api/migration/run/todo-job>
