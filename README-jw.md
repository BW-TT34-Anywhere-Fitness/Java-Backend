# JWT Token implementation - Jiawei
## >>> dev version: You must use localhost:3000 as your frontend origin for CORS<<<


### login:

POST
http://xnor.space/api/authenticate

with format:
{username: "admin", password: "admin"}


---

### get all courses:
GET
http://xnor.space/api/courses

use the auth token like so:
axios.get("http://xnor.space/api/courses", {headers: {Authorization: \`Bearer ${res.data.id_token}\`}})

where `res` is the response of the login POST

---

### register:
POST
http://xnor.space/api/register

with format
{login: "username", password: "password", accounttype: "student/instructor"}
other available fields: firstName, lastName, email

---

### User getting his own info:

GET
http://xnor.space/api/account


---

### instructor posting course:
http://xnor.space/api/courses/

POST
```
{
    "name": "lorem",
    "type": "ipsum",
    "starttime": "2021-04-29T14:58:46", (default unzoned,use 2021-03-29T00:00:00-05:00 for zoned)
    "duration": "PT86400S", (example: PT86400S for 1 day, PT1H43M20S for 1 hour 43 minutes 20 second)
    "intensity": 9001, (integer)
    "location": "lorem",
    "maxsize": 72720
}

```

---
### instructor course update endpoint:
http://xnor.space/api/courses/{id}

### Supports:

1. Partially updating course
PATCH

body example:

{
  starttime: "2100-01-01T00:00:00"
}

2.instructor full overwrite at this endpoint:

PUT

body example:

```
{
    "name": "ipsum",
    "type": "lorem",
    "starttime": "3021-04-29T14:58:46", 
    "duration": "PT200H", 
    "intensity": 5,
    "location": "ipsum",
    "maxsize": 3
}
```

3. instructor delete course with id at this endpoint:

DELETE


---
### student course CRUD endpoint: 
http://xnor.space/api/account/courses/{id}

### Supports:

1.student add oneself to a course at this endpoint:

POST 

2.student remove oneself to a course at this endpoint:

DELETE



---
### student/instructor obtaining their own courses (attending/teaching):

GET

http://xnor.space/api/account/courses

---
### instructor getting courses that they attend:

GET

http://xnor.space/api/account/courses/attending


---
### user modifying their account (except password) (this is subject to change)

POST

http://xnor.space/api/account/

samplebody

```
{
    "login: "user", (you have to supply anything in this field, but this request only change the logged in user I think.)
    "lastName": "testname",
    "fisrtName": "lorem",
}

```










