# Spring-newbie

## Dev Environment
* [Java JDK 21](https://jdk.java.net/archive/)
* [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/download/other.html)
* [Java Spring Boot](https://spring.io)
* [Vue](https://vuejs.org)
* [Vite](https://vite.dev)
* [Vuetify](https://vuetifyjs.com/en/)

## File Directory
```
src/
├── frontend/
│   ├── src/
│   │   ├── main.js
│   │   ├── App.vue
│   │   ├── components/
│   │   └── router/
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
└── main/
    ├── java/org/example/springnewbie/
    │   ├── SpringNewbieApplication.java
    │   ├── WebController.java
    │   ├── Controller/
    │   ├── DTO/
    │   ├── Dao/
    │   └── Service/
    └── resources/
        ├── static
        └── application.properties
```

# API Documentation

## User Management

### 1. Add User
> Add a new user
* method：`POST`
* path：`/v1/user/post/user`

### Request
<b>Body</b>
```json
{
  "name": "String",
  "email": "String",
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


### 2. Fetch User
> Fetch a user info
* method：`GET`
* path：`/v1/user/get/get_user`

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

### 3. Update User
> Update a user info
* method：`PUT`
* path：`/v1/user/put/fix_user`

### Request
**Header**

`email` user wanting updated

**Body**

```json
{
  "new_name": "String",
  "new_email": "String",
  "password": "Hashed String, SHA256 salted"
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

### 4. Delete User
> Delete a user
* method：`DELETE`
* path：`/v1/user/delete/delete_user`

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

