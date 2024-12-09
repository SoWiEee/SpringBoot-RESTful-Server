# Spring-newbie

## Dev Environment
* [Java JDK 21](https://jdk.java.net/archive/)
* [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/other.html)
* [Java Spring](https://spring.io)

# API Documentation

## POST /v1/user/post/user
* Add a new user

### Request
<b>Header</b>


<b>Body</b>
```json
{
  "name": "String",
  "e-mail": "String",
  "password": "Hashed String, SHA256"
}
```

### Response
```json
{
  "rsp_code": "Integer response code",
  "rsp_msg": "String description of response"
}
```

| rsp_code | rsp_msg              |
|----------|----------------------|
| 20       | Update Successful    |
| 21       | Email existed        |
| 30       | Incorrect Parameters |
| 31       | Missing parameters   |


## GET /v1/user/get/get_user
* Fetch a user info

### Request
**Header**

`email` user wanting fetched


### Response
**Body**
```json
{
  "rsp_code": "Integer response code",
  "rsp_msg": "String description of response",
  "data": {
    "name": "String",
    "email": "String"
  }
}
```

| rsp_code | rsp_msg              |
|----------|----------------------|
| 20       | Fetch Successful     |
| 30       | Incorrect Parameters |
| 31       | Missing Parameters   |
| 40       | Email not found      |



## PUT /v1/user/put/fix_user
* Update a user info

### Request
**Header**

`email` user wanting updated

**Body**

```json
{
  "new_name": "String",
  "new_e-mail": "String",
  "password": "Hashed String, SHA256"
}
```

### Response
**Body**
```json
{
  "rsp_code": "Integer response code",
  "rsp_msg": "String description of response"
}
```

| rsp_code | rsp_msg              |
|----------|----------------------|
| 20       | Update Successful    |
| 30       | Incorrect Parameters |
| 31       | Missing Parameters   |
| 40       | Email not found      |
| 50       | Incorrect Password   |



## DELETE /v1/user/delete/delete_user
* Delete a user

### Request
**Header**

`email` user wanting deleted

`password` user wanting deleted



### Response
**Body**
```json
{
  "rsp_code": "Integer response code",
  "rsp_msg": "String description of response"
}
```

| rsp_code | rsp_msg              |
|----------|----------------------|
| 20       | Delete Successful    |
| 30       | Incorrect Parameters |
| 31       | Missing Parameters   |
| 40       | Email not found      |

# Contributors

