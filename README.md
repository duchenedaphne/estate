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
> mvn clean  
> mvn install  
> mvn spring-boot:run

- Front-end :
> git clone https://github.com/duchenedaphne/estate-frontend

Install dependencies:

> npm install

Launch Front-end:

> npm run start

Navigate to `http://localhost:4200/`.

## Swagger UI

The Swagger UI is available at `http://localhost:3001/swagger-ui/index.html`.

Use your Google account to try all the APIs :

1 - Go to the OAuth 2.0 credentials registration
[https://console.developers.google.com/apis/credentials](https://console.developers.google.com/apis/credentials)

2 - click on credentials from the left dashboard 
> -> Create  Credentials -> OAuth client ID

3 - in the form  
. set a name for the client OAuth 2.0 web application.  
. add the JavaScript Origins : http://localhost:3001  
. add both authorized redirect URLs :   
http://localhost:3001/login/oauth2/code/google  
http://localhost:3001/swagger-ui/oauth2-redirect.html

4 - Save your `client ID` and `client secret`, to set them to the `application.properties` file, under the Google properties :
> spring.security.oauth2.client.registration.google.client-id=${CLIENT_ID}  

> spring.security.oauth2.client.registration.google.client-secret=${CLIENT_SECRET} 

5 - in the [swagger UI](http://localhost:3001/swagger-ui/index.html)  
. click on the `Authorize`🔓 button  
. select all the scopes

## Architecture Back-end

- `auth` folder: manage the authentication and registration on the app.
- `config` folder: contains all the configuration files.
- `controllers` folder: Presentation layer - Rest Controllers.
- `services` folder: Business Logic layer. 
- `jpa_repositories` folder: Data Access layer.
- `models` folder: contains all the data entity classes.

## ✍ Author
Daphné Duchêne
