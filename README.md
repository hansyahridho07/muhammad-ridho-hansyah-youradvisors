
# Getting Started

### Author: Muhammad Ridho Hansyah

* Language: Java (21)
* Framework: Spring Boot
* Database: MySQL


## API DOCUMENTATION

### Create Company
Ini digunakan sebagai simulasi penerapan multi tenancy. Maka setiap user akan menjadi user bagi tiap perusahaan.

- Path: POST /api/v1/company/register

- Request Body
```js
  {
    "name": "Default Company",
    "email": "company1@maildrop.cc",
    "code": "CMP01"
}
```

- Response
```js
{
    "message": "Successfully create company"
}
```

### Register User

- Path: POST /api/v1/auth/register

- Headers
```js
company_id // jika menggunakan credential yang saya sediakan, bisa menggunakan id [07dcaa52-1dca-481a-9697-901c947e0aa0]
```

- Request Body
```js
{
    "name": "Ridho",
    "email": "ridho2@maildrop.cc",
    "password": "password"
}
```

- Response
```js
{
    "message": "Successfully register",
    "form": {
        "name": "Ridho",
        "email": "ridho2@maildrop.cc",
        "urlVerification": "http://localhost:3000/api/v1/auth/verify/3abd6d1b-b820-43d7-82b6-7bcd2bee3d0d"
    }
}
```

### Verify Account
Sebelum user bisa login ke account, user harus memverifikasi diri terelbih dahulu. Verifikasi di dapat dari response data saat berhasil register account

- Path: PATCH /api/v1/auth/verify/:token

- Response
```js
{
    "message": "Successfully verified account"
}
```

### Login Account

- Path: POST /api/v1/auth/login

- Headers
```js
company_id // jika menggunakan credential yang saya sediakan, bisa menggunakan id [07dcaa52-1dca-481a-9697-901c947e0aa0]
```

- Request Body
```js
{
    "email": "ridho1@maildrop.cc",
        "password": "password"
}
```

- Response
```js
{
    "message": "Successfully login",
        "form": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWRobzFAbWFpbGRyb3AuY2MiLCJpYXQiOjE3NTIyODI3NTUsImV4cCI6MTc1MjI4NjM1NX0.yxatsZAIMsh1RGtNIenv_LVcredldWaDucO2-SrFxHw",
            "expiresIn": 3600000
    }
}
```

## Form

### Create form

- Path : POST /api/v1/forms

- Headers
```js
Authorization: Bearer
```

- Request Body
```js
{
    "name": "Stacks of Web Tech Members",
        "slug": "member-stacks",
        "allowed_domains": [ "webtech.id" ],
        "description": "To collect all of favorite stacks",
        "limit_one_response": true
} 
```

- Response
```js
{
    "message": "Successfully create form",
        "form": {
        "name": "Stacks of Web Tech Members",
            "slug": "member-stacks",
            "description": "To collect all of favorite stacks",
            "id": 102,
            "limit_one_response": true,
            "creator_id": 1
    }
}
```

### Get All form

- Path : GET /api/v1/forms?page=1&limit=20

- Headers
```js
Authorization: Bearer
```

- Response
```js
{
    "message": "Get all forms success",
        "form": [
        {
            "name": "Stacks of Web Tech Members",
            "slug": "member-stacks",
            "description": "To collect all of favorite stacks",
            "id": 2,
            "limit_one_response": false,
            "creator_id": 1
        }
    ]
}
```

### Get Detail form

- Path : GET /api/v1/forms/:slug

- Headers
```js
Authorization: Bearer
```

- Response
```js
{
    "message": "Get form success",
        "form": {
        "name": "Stacks of Web Tech Members",
            "slug": "member-stacks",
            "description": "To collect all of favorite stacks",
            "id": 52,
            "questions": [
            {
                "id": 2,
                "name": "Most Favorite JS Framework",
                "choices": "React JS,Vue JS,Angular JS,Svelte",
                "form_id": 52,
                "choice_type": "MULTIPLE_CHOICE",
                "is_required": true
            }
        ],
            "limit_one_response": true,
            "creator_id": 1,
            "allowed_domains": [
            "webtech.id"
        ]
    }
}
```


## Question

### Create Question

- Path: POST /api/v1/forms/:form_slug/questions

- Headers
```js
Authorization: Bearer
```

- Request Body
```js
{
    "name": "Most Favorite JS Framework",
        "choice_type": "multiple choice",
        "choices": [
        "React JS",
        "Vue JS",
        "Angular JS",
        "Svelte"
    ],
        "is_required": true
}
```

- Response
```js
{
    "message": "Add question success"
}
```

### Delete Question

- Path: DELETE /api/v1/forms/:form_slug/questions/:question_id

- Headers
```js
Authorization: Bearer
```

- Response
```js
{
    "message": "Remove question success"
}
```

## Answer

### Create Question

- Path: POST /api/v1/forms/:form_slug/responses

- Headers
```js
Authorization: Bearer
```

- Request Body
```js
{
    "answers":[
        {
            "question_id":"2",
            "value":"Benar"
        }
    ]
}
```

- Response
```js
{
    "message": "Submit response success"
}
```