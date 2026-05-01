# Authentication & Security Guide

Cargora uses **JSON Web Token (JWT)** and **Spring Security** to handle authentication and role-based authorization.

## Security Overview

- **Standard:** Stateless JWT authentication.
- **Provider:** `java-jwt` library.
- **Header:** `Authorization: Bearer <token>`.
- **Encryption:** Passwords are encoded (typically using BCrypt) before storage.

## Roles & Permissions

The system defines three main roles:
1.  **ROLE_USER**: Standard customers who can track their packages, update their profiles, and top up their balances.
2.  **ROLE_EMPLOYEE**: Staff members who can create packages, update status/weight, and manage users.
3.  **ROLE_ADMIN**: Full access to all administrative functions, including user management and system-wide configurations.

## Authentication Flow

### 1. Registration (`POST /auth/register`)
Users provide their details including a unique `username` (email), `pin`, and `fullname`. 
- **Validation**: Passwords must meet security complexity requirements.
- **Default Status**: New accounts are `enabled` by default.

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "username": "user@example.com",
  "password": "Password1",
  "pin": "1234567",
  "fullname": {
    "name": "Ali",
    "surname": "Aliyev"
  }
}
```

### 2. Login (`GET /auth/login`)
Users submit their credentials. If valid, the server generates a JWT signed with a secret key.
- **Response**: The API returns the token, the user's email, and their assigned role.

```http
GET /auth/login
Content-Type: application/json
```

```json
{
  "username": "user@example.com",
  "password": "Password1"
}
```


Both endpoints return:

```json
{
  "token": "jwt-token",
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

### 3. Authorized Requests
For any protected endpoint (e.g., `/packages/create-new`), the client must include the token in the HTTP header:
```http
Authorization: Bearer <your_jwt_token_here>