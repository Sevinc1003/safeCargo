### Auth

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/auth/register` | Register a new account |
| `GET` | `/auth/login` | Login and receive a JWT token |

### Packages

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/packages/create-new` | Create a package. Requires `ADMIN` or `EMPLOYEE` role. |
| `GET` | `/packages/{id}` | Get package details by database id |
| `GET` | `/packages/tracking-code/{code}` | Track a package by external or internal tracking code |
| `GET` | `/packages/of-user/{PIN}` | List packages by user PIN |
| `GET` | `/packages/status/{status}` | List packages by status |
| `PATCH` | `/packages/update-weight` | Update package weight and shipping fee |
| `PATCH` | `/packages/update-destinationBranch` | Update destination pickup point |
| `GET` | `/packages/filter-by` | Filter packages by optional query parameters |

Supported package statuses:

```text
DECLARED
AT_FOREIGN_WAREHOUSE
ON_THE_WAY
DECLARATION
ARRIVED
DELIVERED
```

Create package example:

```json
{
  "userId": 1,
  "destinationBranchId": 1,
  "trackingNumber": "TRK123456",
  "weight": 2.5
}
```

Update weight example:

```json
{
  "packageId": 1,
  "weight": 3.2
}
```

Filter example:

```http
GET /packages/filter-by?pin=1234567&status=ARRIVED&minWeight=1&maxWeight=5
```

### Package History

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/package-history/{packageId}` | Get status history for a package |

### Users

| Method | Path | Description |
| --- | --- | --- |
| `PUT` | `/user/home-address` | Update the authenticated user's home address |
| `PUT` | `/user/pickup-point` | Update the authenticated user's pickup point |
| `PUT` | `/user/{userId}/update-address` | Update a user's address. Requires `ADMIN` or `EMPLOYEE` role. |
| `PUT` | `/user/{userId}/update-pickup-point` | Update a user's pickup point. Requires `ADMIN` or `EMPLOYEE` role. |
| `PATCH` | `/user/disable-user/{userId}` | Disable a user. Requires `ADMIN` or `EMPLOYEE` role. |
| `PATCH` | `/user/enable-user/{userId}` | Enable a user. Requires `ADMIN` or `EMPLOYEE` role. |

### Balance

| Method | Path | Description |
| --- | --- | --- |
| `PATCH` | `/balance/top-up` | Add funds to a user's balance |

Top-up example:

```json
{
  "userId": 1,
  "amount": 25.00,
  "cardNumber": "4111111111111111",
  "cvv": "123"
}
```
