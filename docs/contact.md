# Contact API Spec

## Create Contact
Endpoint: POST /api/contacts

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "firstName": "Hasan",
  "lastName": "Hasan",
  "email": "hasan@mail.com",
  "phone": "08123123"
}
```

Response Body (Success):
```json
{
  "data": {
    "id": "random_string",
    "firstName": "Hasan",
    "lastName": "Hasan",
    "email": "hasan@mail.com",
    "phone": "08123123"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "Invalid email format, invalid phone format, ..."
}
```

## Update Contact
Endpoint: PUT /api/contacts/{contactId}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "firstName": "Hasan",
  "lastName": "Hasan",
  "email": "hasan@mail.com",
  "phone": "08123123"
}
```

Response Body (Success):
```json
{
  "data": {
    "id": "random_string",
    "firstName": "Hasan",
    "lastName": "Hasan",
    "email": "hasan@mail.com",
    "phone": "08123123"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "Invalid email format, invalid phone format, ..."
}
```

## Get Contact
Endpoint: GET /api/contacts/{contactId}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": {
    "id": "random_string",
    "firstName": "Hasan",
    "lastName": "Hasan",
    "email": "hasan@mail.com",
    "phone": "08123123"
  }
}
```

Response Body (Failed, 404):
```json
{
  "errors": "Contact is not found."
}
```

## Search Contact
Endpoint: GET /api/contacts

Query Param:
- name: String; Contact first name or last name; Using LIKE query; Optional
- phone: String; Contact phone; Using LIKE query; Optional
- email: String; Contact email; Using LIKE query; Optional
- page: Integer; Start from 0; Default 0
- size: Integer; Default 10

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": [
    {
      "id": "random_string",
      "firstName": "Hasan",
      "lastName": "Hasan",
      "email": "hasan@mail.com",
      "phone": "08123123"
    }
  ],
  "paging": {
    "currentPage": 1,
    "totalPage": 15,
    "size": 10
  }
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Unauthorized."
}
```

## Remove Contact
Endpoint: DELETE /api/contacts/{contactId}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": "OK"
}
```

Response Body (Failed):
```json
{
  "errors": "Contact is not found."
}
```