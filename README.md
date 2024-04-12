# Password Manager API

This project is a Password Manager API developed using Java, Spring Boot, and Maven. It provides a secure way to manage passwords for different users. The API allows users to create, update, and delete passwords. Each password can be tagged for easy categorization and retrieval. The project also includes a user service for managing users and their password limits.

## Running the Project

To start the project, navigate to the project directory and run the following command:

```bash
docker-compose up -d
```

This command will start all the services defined in the `docker-compose.yml` file.

Please note that you need to have Docker and Docker Compose installed on your machine to run the project using this method.

## API Endpoints

### User

- `GET /v1/users/{id}`: Get details of a user.
- `POST /v1/users`: Create a new user.
- `PUT /v1/users/{id}`: Update an existing user.
- `DELETE /v1/users/{id}`: Delete a user.

### Password

- `GET /v1/passwords/user/{id}`: Get all passwords of a user.
- `POST /v1/passwords`: Create a new password.
- `PUT /v1/passwords/{id}`: Update an existing password.
- `DELETE /v1/passwords/{id}`: Delete a password.

## API Documentation

The complete API documentation, including all endpoints and details about request and response parameters, is available on Swagger UI. You can access the Swagger UI by visiting the following URL when the application is running:

```
http://localhost:8080/swagger-ui.html
```

## Testing

The project includes unit tests for the services. The tests are written using JUnit and Mockito.

## License

[MIT](https://choosealicense.com/licenses/mit/)
