# GitHub Repositories API

A REST API application that consumes the GitHub API v3. It allows users to fetch a list of non-fork repositories belonging to a specific GitHub user, along with their respective branches and last commit SHAs.

## Tech Stack
* **Java 17**
* **Spring Boot 3.5.10**
* **Spring Cloud OpenFeign** (for declarative REST client)
* **Lombok** (to reduce boilerplate code)
* **Maven** (build automation tool)

## Prerequisites
To run this project, you will need:
* Java 17 or higher installed
* Maven installed (or use the provided Maven wrapper)

## How to Run

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```
   
2. **Configure GitHub Token (Optional but recommended):**
   To bypass the GitHub API rate limit for unauthenticated requests, you need to provide your own GitHub Personal Access Token. 
   Open src/main/resources/application.properties and add your token:
```bash 
github.api.token=Bearer YOUR_GITHUB_TOKEN_HERE
```
   If the token is not provided, the application will still work but will be subject to strict rate limits.

3. **Build and run the application using Maven:**

```bash 
mvn spring-boot:run
```
Alternatively, you can build the .jar file and run it:

```bash 
mvn clean package
java -jar target/zad-dom-02-02-0.0.1-SNAPSHOT.jar
```
The application will start on http://localhost:8080.

## API Endpoints
Returns all non-fork repositories for a given GitHub user.

**Request:** GET http://localhost:8080/{username}

**Required Headers:**  Accept: application/json

## Responses & Examples
1. **Successful Response (200 OK)**

   Condition: User exists and the Accept: application/json header is provided.
```bash 
{
  "ownerLogin": "kalqa",
  "data": [
    {
      "repositoryName": "03-open-feign",
      "branches": [
        {
          "name": "master",
          "sha": "8930ab9c05b9fdbd0b5daa5ab6468d5f4ecdae1f"
        }
      ]
    }
  ]
}
```

2. **User Not Found (404 Not Found)**
Condition: The Provided GitHub username does not exist.
```bash 
{
  "status": 404,
  "Message": "User not found"
}
```

3. **Unacceptable format (406 Not Acceptable)**
Condition: The client requests format other than JSON 
```bash 
{
  "status": 406,
  "Message": "Accept header must be application/json"
}
```