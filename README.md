# Spring Boot + Angular 2 Boilerplate

Quick start for spring boot + angular 2 projects with JWT auth

## Includes:

Front-end:

- angular-cli boilerplate files
- Authentication service

Back-end:

- Embedded tomcat server
- Gradle build file
- Boilerplate files
- JWT authentication

## Build and Run

First of all you need to configure database. Properties are located in `./backend/src/main/resources/application.properties` file. By default application is using PostgreSQL database(name: `test`, user: `test`, password: `test`).

Also you need to configure JWT secret in file listed above.

1. Run `npm install --prefix ./frontend` to install front-end dependencies.
2. Run `npm run build --prefix ./frontend` to build angular application.
3. Run `gradle -p ./backend build` to build a spring boot application.
4. Run `gradle -p ./backend bootRun` or `java -jar backend/build/libs/app-name-[version].jar` to start spring boot application on embedded server. By default server will be running on port `8080`.

> `npm start --prefix ./frontend` to start front-end server for development.

> By default server will be running on port `4200`

## Testing

- `npm test --prefix ./frontend` - to run front-end unit tests.
- `npm run e2e --prefix ./frontend` - to run end to end tests.
- `gradle -p ./backend test` - to run server tests.
