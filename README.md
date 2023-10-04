# Estate by ChâTop

🏡 Find your next vacation rental and offer your properties for seasonal rental.

## 🛠 Software tools

- [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3
- Node.js
- [Java 17](https://www.oracle.com/java/technologies/downloads/)
- Spring Boot
- [MySQL](https://www.mysql.com/fr/downloads/)

## Start the project

- MySQL :

Create a database with the name `estate_db`.

- Back-end :  

Clone this repository :
> git clone https://github.com/duchenedaphne/estate

Add your database credentials to the application.properties file :
>spring.datasource.username=${DB_USER}

>spring.datasource.password=${DB_PASSWORD}

Launch the backend server with Spring Boot and Maven :  
> mvn spring-boot:run

- Front-end :

Clone the `estate-frontend` repository :
> git clone https://github.com/duchenedaphne/estate-frontend

Install the dependencies :

> npm install

Launch the frontend :

> npm run start

Navigate to http://localhost:4200/.

## Swagger UI

The Swagger UI is available at http://localhost:3001/swagger-ui/index.html.

To get access to all the APIs :

1 - try the `POST` /api/auth/register endpoint from the `auth-controller`.

2 - copy the token value from the `Response body`.

3 - click on the `Authorize`🔓 button.

4 - paste the token value into the field.

## Architecture Back-end

- `auth` folder : manage the users authentication and registration on the app.
- `config` folder : contains all the configuration files.
- `controllers` folder : contains the Presentation layer with the Rest Controllers.
- `services` folder : contains the Business Logic layer. 
- `repositories` folder: contains the Data Access layer.
- `models` folder : contains all the data entity classes.
- `jpo` folder : contains the java plain objects.

### ⚠ Warning
This project was built with VSCode,
that results of some wrong code indentation for some classes, depending on your IDE.

## ✍ Author
Daphné Duchêne
