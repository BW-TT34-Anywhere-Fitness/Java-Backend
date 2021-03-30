# JWT Token implementation - Jiawei
## >>> dev version: You must use localhost:3000 as your frontend origin for CORS<<<


login:

POST
http://xnor.space/api/authenticate

with format:
{username: "admin", password: "admin"}


---

all courses:
GET
http://xnor.space/api/courses

use the auth token like so:
axios.get("http://xnor.space/api/courses", {headers: {Authorization: \`Bearer ${res.data.id_token}\`}})

where `res` is the response of the login POST

---

register:
POST
http://xnor.space/api/register

with format
{login: "username", password: "password", accounttype: "student/instructor"}
other available fields: firstName, lastName, email

---

userinfo:

GET
http://xnor.space/api/account


---

instructor posting course:
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
instructor course update endpoint:
http://xnor.space/api/courses/{id}

### Supports:

PATCH

body example:

{
  starttime: "2100-01-01T00:00:00"
}

instructor full overwrite at this endpoint:

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

instructor delete course with id at this endpoint:

DELETE


---
student course CRUD endpoint: 
http://xnor.space/api/account/courses/{id}

### Supports:

student add oneself to a course at this endpoint:

POST 

student remove oneself to a course at this endpoint:

DELETE



---
student/instructor obtaining their own courses:

GET

http://xnor.space/api/account/courses




# Java-Backend (Pat)



