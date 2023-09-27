# Estate by ChâTop

🏡 Find your next vacation rental.

## 🛠 Software tools

- [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3
- Node.js
- [Java 11](https://www.oracle.com/java/technologies/downloads/) last LTS version 11.0.20
- Spring Boot
- [MySQL](https://www.mysql.com/fr/downloads/)

## Start the project

- MySQL :

The database name is `estate_db`.

- Back-end :
> git clone https://github.com/duchenedaphne/estate

Add your database credentials to the application.properties file :
>spring.datasource.username=${DB_USER}

>spring.datasource.password=${DB_PASSWORD}

Launch the backend server with Spring Boot and Maven :
> mvn spring-boot:run

- Front-end :
> git clone https://github.com/duchenedaphne/estate-frontend

Install dependencies:

> npm install

Launch Front-end:

> npm run start

Navigate to `http://localhost:4200/`.

## Swagger

The Swagger UI is available on `http://localhost:3001/swagger-ui/index.html`.

## Architecture Back-end

- `auth` folder: manage the authentication and registration on the app.
- `config` folder: contains all the configuration files.
- `controllers` folder: Presentation layer - Rest Controllers.
- `services` folder: Business Logic layer. 
- `jpa_repositories` folder: Data Access layer.
- `models` folder: contains all the data entity classes.
