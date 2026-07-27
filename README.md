# Lokit

Office building access control system

## About the project

Lokit is a microservice backend for an office RBAC system.  
Users, fetched from an external IdP, can be assigned roles, which allow them to access rooms in their office.  
Entry to the room is checked by a reader device that scans the user's RFID card, makes a request to the backend and
based on the decision received opens (or not) the door.

## Tech stack

- Java 21
- Spring Boot 4.1
- PostgreSQL (and H2DB for development)
- Flyway
- gRPC for synchronous communication between microservices
- RabbitMQ for asynchronous communication between microservices
- Redis
- Keycloak
- openssl for mTLS certificate generation
- Docker / Docker Compose
- NGINX as a reverse proxy to the internal Docker network

## How to run

The following instructions are meant for Linux.  
On Windows, the mTLS certificates will have to be generated either through WSL, Git Bash, or without using the `certgen`
script at all.

- Clone the repository to your device by using:
  `git clone https://github.com/Eukon05/lokit.git`
- Open the terminal in the folder with the repo.
- Execute the following commands:

```shell
cd certgen
chmod +x certgen.sh
chmod -R +x scripts
./certgen.sh
```

This will generate mTLS certificates, that the microservices use to secure their gRPC connections.

- Return to the main directory of the repo with `cd ..`

Before starting up the project, you'll have to prepare a `.env.docker` file.  
Please create a copy of the `.env.docker.example` file and name it `.env.docker`.  
The file is split into four section:

- Infrastructure
- PostgreSQL
- Auth
- TLS passwords

The infrastructure section can be set up in any way you like. The same goes for PostgreSQL.  
For auth section, though, you'll have to make it match your Keycloak settings, which you will set up in the next
steps.  
If you have generated the mTLS certificates using the helper `certgen` script, set all passwords to `changeit`.

Please set `LOKIT_KEYCLOAK_PUBLIC_URL` to the URL you will be accessing Keycloak from.  
For local development, it will most likely be `http://localhost:LOKIT_KEYCLOAK_PORT` (please replace LOKIT_KEYCLOAK_PORT
with the value set for it).

Use the following command to start KEYCLOAK ONLY:  
`docker compose --env-file .env.docker up -d keycloak`

Open the Keycloak admin panel in your web browser of choice by going to the Keycloak public URL.

- In Keycloak, go to the "Manage Realms" section and create a new Realm
- Once in the new Realm, go to the "Clients" section and create a new Client. During the setup, make sure to enable "
  Client Authentication" and "Service Account Roles".
- For "Root URL", set `http://identity-service:8080`
- For "Valid redirect URIs", set `http://identity-service:8080/*`
- For both "Valid post logout redirect URIs" and "Web origins" set `+`
- Save, and open the "Service account roles" section
- Open "Assign role -> Client roles" and make sure the following are selected:
    - query-users
    - view-users
- Go to the "Credentials" section and copy the "Client Secret" value. In your modified env file, fill in the Realm,
  Client ID and Client Secret

That concludes the setup of the Keycloak CLIENT, but we still have to determine what source we'll use for roles.  
Most services expect the user to have a `LOKIT_ADMIN` role assigned to access their endpoints.

A quick way to add this, is to go to:  
`Realm roles -> Create role` and setting `LOKIT_ADMIN` as the name. You'll be able to assign this role to the users
later.  
In the env file, set `LOKIT_JWT_ROLE_CLAIMS` to `realm_access.roles` to finish the Keycloak configuration.

After Keycloak and the env file are configured, the only thing left to do to run the app is to execute:  
`docker compose --env-file .env.docker up -d --build`