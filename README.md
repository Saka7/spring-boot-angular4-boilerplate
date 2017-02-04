# Spring Boot, Angualr 2 Boilerplate

## Includes:

Front-end:

- Boilerplate files
- Authentication service

Back-end:

- Boilerplate files
- JWT authentication

## Build and Run

1. Run `./build-frontend.sh` to build the angular app
2. Run `./build-backend.sh` to build the spring boot app
3. Run `java -jar backend/build/libs/app-name-[version].jar` to start spring boot application on embedded server

- You can build front-end and backend using one command `./build-all.sh`
- You can run angular app using `cd frontend && ng serve`
- You can run spring boot app using `cd backend && gradle bootRun`
