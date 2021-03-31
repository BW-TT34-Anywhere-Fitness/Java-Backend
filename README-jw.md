# JWT Token implementation - Jiawei
## >>> dev version: You must use localhost:3000 as your frontend origin for CORS<<<


### login:

POST
http://xnor.space/api/authenticate

with format:
```
{username: "testuser", password: "testuser"}
```

---

### get all courses:
GET
http://xnor.space/api/courses

use the auth token like so:
axios.get("http://xnor.space/api/courses", {headers: {Authorization: \`Bearer ${res.data.id_token}\`}})

where `res` is the response of the login POST

---

### get all course types:

GET

http://xnor.space/api/courses/categories or http://xnor.space/api/courses/types


---

### register:
POST
http://xnor.space/api/register

with format
```
{login: "username", password: "password", accounttype: "student/instructor"}
```
the above 3 fields are required, other available fields you can supply: firstName, lastName, email

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

#### Supports:

1. Partially updating course
PATCH

body example:

{
  starttime: "2100-01-01T00:00:00"
}

2. instructor full overwrite at this endpoint:

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

#### Supports:

1. student add oneself to a course at this endpoint:

POST 

2. student remove oneself to a course at this endpoint:

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
### user modifying their account info

POST

http://xnor.space/api/account/


login field is required, but you can supply anything there, since this request only modifies the logged in user, only, lastName fisrtName, email are modifiable.

samplebody

```
{
    "login: "anythinghere",
    "lastName": "testname",
    "fisrtName": "lorem",
    "email": "new@email.com",
}

```

### user modifying their own password

POST

http://xnor.space/api/account/change-password

with body

```
{
    "currentPassword" : "currentpass",
    "newPassword": "newpass"
}

```



### Search
GET
http://xnor.space/api/courses/search?field=term&..


- all min max are containing (i.e. mindate = 2021-04-01 will return results containing courses that start on April first)
- type, name, location are all partial matches (i.e. whether the string contains the search query term)
- you can leave any of the search term out
- therefore GET http://xnor.space/api/courses/search = GET http://xnor.space/api/courses/


1. mnt = minimum time, format below
1. mxt = maximum time
1. mnd = minimum date, format below
1. mxd = maximum date
1. mndr = min duration, format below
1. mxdr = max duration
1. mni = min intensity, 0-10 
1. mxi = max intensity, 0-10
1. type = type, string
1. name = name, string
1. loc = location, string
1. mns = min class maxsize, integer
1. mxs = max class maxsize, integer
1. nf = 0 or 1 = whether the class is not full, use 1 to only show class that isn't full, etc.
1. ins = instructor's username(login), this field is case insensitive exact matching (john would return instructor John, joHN, etc.)


sample search url:

http://xnor.space/api/courses/search?mnt=00:00:00&mxt=23:59:59&mnd=1970-01-01&mxd=2100-12-31&mndr=PT0.000S&mxdr=PT24H&mni=0&mxi=10000&type=lorem&name=ipsum&loc=mars&nf=1&ins=testinstructor








