# Flip URL Shortener Service

## Overview

This project is a URL shortener service that allows users to shorten long URLs and retrieve the original long URLs using
the shortened versions. The service provides endpoints for creating short URLs and resolving them back to their original
long URLs.

## Features

- Shorten a long URL to a unique short URL.
- Retrieve the original long URL using the shortened URL.
- Simple and intuitive API with Swagger documentation.

## Getting Started

### Prerequisites

- Java 21
- Spring Boot
- Maven (for dependency management and building the project)

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/engezozlem/flip-url-shortener.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd flip-url-shortener
    ```

3. **Build the project using Maven:**

    ```bash
    mvn clean install
    ```

4. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

### API Endpoints

#### Shorten a URL

- **Endpoint:** `POST /api/v1/urls/shorten`
- **Description:** Receives a long URL and returns a shortened URL.
- **Request Body:**

    ```json
    {
      "longUrl": "https://open.spotify.com/track/0ofHAoxe9vBkTCp2UQIavz?si=84f546fbc6df4d63"
    }
    ```

- **Responses:**

    - **200 OK:**

        ```json
        {
          "shortUrl": "IA6yiA80"
        }
        ```

    - **400 Bad Request:**

        ```json
        {
          "errors": {
            "longUrl": "Long URL must start with http:// or https://"
          }
        }
        ```

#### Retrieve Original URL

- **Endpoint:** `GET /api/v1/urls/{shortUrl}`
- **Description:** Receives a shortened URL and returns the original long URL.
- **Responses:**

    - **200 OK:**

        ```json
        {
          "longUrl": "https://open.spotify.com/track/0ofHAoxe9vBkTCp2UQIavz?si=84f546fbc6df4d63"
        }
        ```

    - **404 Not Found:**

        ```json
        {
          "errors": {
            "error": "URL not found"
          }
        }
        ```

### Swagger UI

You can access the Swagger UI for the API documentation and testing via this [link](http://localhost:8080).

### Roadmap

- TODO-1 : Authentication layer
- TODO-2 : Storing can be done in Redis
- TODO-3 : Short url check for forbidden urls
- TODO 4 : domain name char checker (like min 1) 
- TODO-5 : Circuit Breaker check
- TODO-6 : Apply a load test
- TODO-7 : Dockerize
- TODO-8 : pom.xml version names
- TODO-9 : Magic string check
- TODO-10 : Regex, SB cost check
- TODO-11 : Can we use get instead o post?
- TODO-12 : add warm up
- TODO-13 : health check
- TODO-14 : create temp object for domain and schema names. we are creating 2 URI objects for each url. avoid unnecessary call

### Error Handling

The service uses global exception handling to provide consistent error responses. Common exceptions include:

- **Global Exception:** Returns a generic error message with HTTP status 500 (Internal Server Error).
- **URL Not Found Exception:** Returns a 404 (Not Found) status when the requested shortened URL does not exist.

### Configuration

- **Swagger Config:** Customizes Swagger UI with contact information and API details.
- **Redirect Controller:** Redirects the root URL to the Swagger UI for ease of access.

### License

This project is licensed under the [MIT License]().

### Contact

For any questions or feedback, you can reach out to:

- **Name:** Özlem Engez
- **Email:** engezozlem8@gmail.com

## Contributing

Contributions are welcome! Please refer to the [CONTRIBUTING.md](CONTRIBUTING.md) file for guidelines.

## Acknowledgments

- Thanks to the [Spring Boot](https://spring.io/projects/spring-boot) and [Swagger](https://swagger.io/) communities for
  their support and tools.



