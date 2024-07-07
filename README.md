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

## Additional Information
### Running Tests
 - The tests are made with JUnit and Mockito, and there are also integration tests. Tests can be run with Gradle:

   ```bash
   ./gradlew test

## Token Authentication:
 -I've added a bit of complexity to the exercise by implementing expirable tokens for authentication. This API uses token-based authentication (JWT - JSON Web Token) to improve security. The tokens are generated with a 20 minute expiration, so clients must include the token in the header of their requests. 
