
# Blog Management REST API

This project is a simple REST API for managing blog posts, including user authentication using JWT. The API allows users to register, log in, and perform CRUD operations on blog posts. Authentication is required for creating, updating, and deleting posts.

---

## Features
- User registration with email and password.
- JWT-based user authentication.
- Create, read, update, and delete (CRUD) operations for blog posts.
- Authentication and authorization checks for protected endpoints.

---

## Technologies Used
- **Backend Framework**: Spring Boot
- **Authentication**: JSON Web Token (JWT)
- **Database**: MySQL
- **ORM**: Hibernate

---

## Setup Instructions

### Prerequisites
1. Java 17 or above
2. MySQL Database
3. Maven

### Steps to Run
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. Update the database configuration in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/blog_api
   spring.datasource.username=<your-mysql-username>
   spring.datasource.password=<your-mysql-password>
   ```

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. The API will be available at:
   ```
   http://localhost:8080/api
   ```

---

## Endpoints

### Authentication

#### 1. Register User
- **URL**: `/api/auth/register`
- **Method**: `POST`
- **Description**: Register a new user.
- **Request Body**:
  ```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "message": "User registered successfully",
    "token": "<JWT_TOKEN>"
  }
  ```

#### 2. Login User
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Description**: Log in with email and password to receive a JWT token.
- **Request Body**:
  ```json
  {
    "email": "john.doe@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "token": "<JWT_TOKEN>"
  }
  ```

---

### Posts

#### 3. Get All Posts
- **URL**: `/api/posts`
- **Method**: `GET`
- **Description**: Retrieve all blog posts.
- **Response**:
  ```json
  [
    {
      "id": 1,
      "content": "This is the first post",
      "createdAt": "2024-12-30T12:00:00",
      "updatedAt": "2024-12-30T12:00:00",
      "authorId": {
        "id": 1,
        "name": "John Doe",
        "email": "john.doe@example.com"
      }
    }
  ]
  ```

#### 4. Get Post by ID
- **URL**: `/api/posts/{id}`
- **Method**: `GET`
- **Description**: Retrieve a specific post by its ID.
- **Response**:
  ```json
  {
    "id": 1,
    "content": "This is the first post",
    "createdAt": "2024-12-30T12:00:00",
    "updatedAt": "2024-12-30T12:00:00",
    "authorId": {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  }
  ```

#### 5. Create a Post
- **URL**: `/api/posts`
- **Method**: `POST`
- **Description**: Create a new post. Must be authenticated.
- **Headers**:
  ```http
  Authorization: Bearer <JWT_TOKEN>
  ```
- **Request Body**:
  ```json
  {
    "content": "This is a new post"
  }
  ```
- **Response**:
  ```json
  {
    "id": 2,
    "content": "This is a new post",
    "createdAt": "2024-12-30T12:10:00",
    "updatedAt": "2024-12-30T12:10:00",
    "authorId": {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  }
  ```

#### 6. Update a Post
- **URL**: `/api/posts/{id}`
- **Method**: `PUT`
- **Description**: Update an existing post. Must be authenticated, and the user must be the author.
- **Headers**:
  ```http
  Authorization: Bearer <JWT_TOKEN>
  ```
- **Request Body**:
  ```json
  {
    "content": "Updated content for the post"
  }
  ```
- **Response**:
  ```json
  {
    "id": 1,
    "content": "Updated content for the post",
    "createdAt": "2024-12-30T12:00:00",
    "updatedAt": "2024-12-30T12:20:00",
    "authorId": {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  }
  ```

#### 7. Delete a Post
- **URL**: `/api/posts/{id}`
- **Method**: `DELETE`
- **Description**: Delete a post. Must be authenticated, and the user must be the author.
- **Headers**:
  ```http
  Authorization: Bearer <JWT_TOKEN>
  ```
- **Response**:
  ```http
  HTTP/1.1 204 No Content
  ```

---

## Error Handling

- **401 Unauthorized**: Returned if the JWT token is missing, invalid, or expired.
- **403 Forbidden**: Returned if the user attempts to update/delete a post they do not own.
- **400 Bad Request**: Returned if the email is already registered.

---

## License
This project is licensed under the MIT License. You are free to use, modify, and distribute it as you like.
