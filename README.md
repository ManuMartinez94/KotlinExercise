# Albums and Photos API

API for managing albums and their associated photos. Built with Spring Boot.

## Technologies Used
- Kotlin
- Java
- Spring Boot
- Swagger (OpenAPI) for API documentation

## Prerequisites
- JDK 17 or higher
- Maven

## Getting Started
Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Installation
1. Clone the repository:
   ```
   git clone https://github.com/ManuMartinez94/KotlinExercise.git
   ```
2. Install Dependencies:
    ```bash
   ./gradlew build
3. Run
   ```bash
   ./gradlew bootRun

### Postman

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/21551936-dd274cc9-cce6-4830-a3b7-daa42dd7c644?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D21551936-dd274cc9-cce6-4830-a3b7-daa42dd7c644%26entityType%3Dcollection%26workspaceId%3Dd04e1323-4ba9-43c1-9107-3d7d892ecd7b)

### Swagger
To view the API documentation, navigate to the following URL in your browser:
```
http://localhost:8080/swagger-ui/index.html
```

## Additional Information
### Running Tests
- The tests are made with JUnit and Mockito, and there are also integration tests. Tests can be run with Gradle:

  ```bash
  ./gradlew test

### Token Authentication:
- I've added a bit of complexity to the exercise by implementing expirable tokens for authentication. This API uses token-based authentication (JWT - JSON Web Token) to improve security. The tokens are generated with a 20 minute expiration, so clients must include the token in the header of their requests. 

### Optimal acquisition of the albums with the photos
- To optimise performance, I first receive the list of albums. Then, using a thread pool configured to handle multiple concurrent tasks, I receive the photos in parallel, reducing the overall waiting time.
