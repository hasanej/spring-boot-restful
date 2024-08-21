# User API Spec

## Register User
Endpoint: POST /api/users

Request Body:
```json
{
  "username": "hasan",
  "password": "secret",
  "name": "Hasan Hasan"
}
```

Response Body (Success):
```json
{
  "data": "OK"
}
```

Response Body (Failed):
```json
{
  "errors": "Username cannot blank."
}
```

## Login User
Endpoint: POST /api/auth/login

Request Body:
```json
{
  "username": "hasan",
  "password": "secret"
}
```

Response Body (Success):
```json
{
  "data": {
    "token": "TOKEN",
    "expired_at": 100000 // Millisecond
  }
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Incorrect username or password."
}
```

## Get User
Endpoint: GET /api/users/current

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": {
    "username": "hasan",
    "name": "Hasan Hasan"
  }
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Unauthorized."
}
```

## Update User
Endpoint: PATCH /api/users/current

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "name": "Hasan New", // If filled, then name is updated
  "password": "newpassword" // If filled, then password is updated
}
```

Response Body (Success):
```json
{
  "data": {
    "username": "hasan",
    "name": "Hasan Hasan"
  }
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Unauthorized."
}
```

## Logout User
Endpoint: DELETE /api/auth/logout

Request Header:
- X-API-TOKEN: Token (Mandatory)

 Response Body (Success):
```json
{
  "data": "OK"
}
```