## Address API Spec

## Create Address
Endpoint: POST /api/contacts/{idContact}/addresses

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "street": "Jl. Jalan",
  "city": "Jakarta",
  "province": "Central Jakarta",
  "country": "Indonesia",
  "postalCode": "11233"
}
```

Response Body (Success):
```json
{
  "data": {
    "id": "random_string",
    "street": "Jl. Jalan",
    "city": "Jakarta",
    "province": "Central Jakarta",
    "country": "Indonesia",
    "postalCode": "11233"
  }
}
```

Response Body (Failed):

```json
{
  "errors": "Contact is not found."
}
```

## Update Address
Endpoint: PUT /api/contacts/{idContact}/addresses/{idAddress}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "street": "Jl. Jalan",
  "city": "Jakarta",
  "province": "Central Jakarta",
  "country": "Indonesia",
  "postalCode": "11233"
}
```

Response Body (Success):
```json
{
  "data": {
    "id": "random_string",
    "street": "Jl. Jalan",
    "city": "Jakarta",
    "province": "Central Jakarta",
    "country": "Indonesia",
    "postalCode": "11233"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "Contact is not found."
}
```

## Get Address
Endpoint: GET /api/contacts/{idContact}/addresses/{idAddresses}

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": {
    "id": "random_string",
    "street": "Jl. Jalan",
    "city": "Jakarta",
    "province": "Central Jakarta",
    "country": "Indonesia",
    "postalCode": "11233"
  }
}
```

Response Body (Failed):
```json
{
  "errors": "Address is not found."
}
```

## Remove Address
Endpoint: DELETE /api/contacts/{contactId}/addresses/{addressId}

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
  "errors": "Address is not found."
}
```

## List Address
Endpoint: GET /api/contacts/{idContact}/addresses

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": [
    {
      "id": "random_string",
      "street": "Jl. Jalan",
      "city": "Jakarta",
      "province": "Central Jakarta",
      "country": "Indonesia",
      "postalCode": "11233"
    }
  ]
}
```

Response Body (Failed):
```json
{
  "errors": "Contact is not found."
}
```