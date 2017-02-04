# Spring Boot, Angualr 2 Boilerplate

Quick start for spring boot + angular 2 projects

## Includes:

Front-end:

- angular-cli Boilerplate files
- Authentication service

Back-end:

- Gradle build file
- Boilerplate files
- JWT authentication

## Build and Run

1. Run `./build-frontend.sh` to build an angular app
2. Run `./build-backend.sh` to build a spring boot app
3. Run `java -jar backend/build/libs/app-name-[version].jar` to start spring boot application on embedded server

## Additional

- You can build front-end and backend using one command `./build-all.sh`
- You can run angular app using `cd frontend && ng serve`
- You can run spring boot app using `cd backend && gradle bootRun`
