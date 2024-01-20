# Reactive Users Microservice

This project implements a reactive microservice using Spring Flux to manage user and department data. The microservice provides various operations for creating, retrieving, and managing users and departments.

## User Controller

### 1. Create a new user

**Endpoint:** `POST /users`

This operation creates a new user and returns the user details reactively.

### 2. Retrieve user information by email and password

**Endpoint:** `GET /users/{email}?password={password}`

This operation retrieves specific user information based on the provided email and password.

### 3. Retrieve all users

**Endpoint:** `GET /users`

This operation reactively returns details of all users saved in the service.

### 4. Retrieve users by criteria

**Endpoint:** `GET /users?criteria={criteria}&value={value}`

This operation returns user information based on specified criteria and value.

### 5. Link user to department

**Endpoint:** `PUT /users/{email}/department`

This operation links a user to a department based on the provided email and department details.

### 6. Delete all user data

**Endpoint:** `DELETE /users`

This reactive action helps test the service by deleting all user data saved in the service.

## Department Controller

### 1. Create a new department

**Endpoint:** `POST /departments`

This operation creates a new department and returns the department details reactively.

### 2. Retrieve department information by department ID

**Endpoint:** `GET /departments/{deptId}`

This operation retrieves specific department information based on the provided department ID.

### 3. Retrieve all departments

**Endpoint:** `GET /departments`

This operation reactively returns details of all departments saved in the service.

### 4. Delete all department data

**Endpoint:** `DELETE /departments`

This reactive action helps test the service by deleting all department data saved in the service.

## Additional Information

- The project uses Spring WebFlux for reactive programming.
- User details include email, name, password, birthdate, recruitdate, and roles.
- Department details include deptId, departmentName, and creationDate.
- The UserController and DepartmentController classes handle HTTP requests for user and department-related operations.
- Incorrect inputs or interface activation will cause operations to return appropriate error codes.
- The order of users or departments returned in GET operations is not important.

Feel free to explore and contribute to this Spring Flux project. For further details on data structures and operations, please refer to the UserController, DepartmentController classes, and associated documentation.

## Getting Started

1. Clone the repository.
2. Build and run the application using your preferred Java IDE or build tool.
3. Test the provided endpoints using tools like Postman or curl.
