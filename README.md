# JWT Token implementation - Jiawei
## >>> dev version: You must use localhost:3000 as your frontend origin for CORS<<<



---

### register:
POST
https://xnor.space/api/register

with format
```
{login: "username", password: "password", accounttype: "student/instructor",  email: "test@email.com"}
```
the above 4 fields are required, other available fields you can supply: firstName, lastName

---

### login:

POST
https://xnor.space/api/authenticate

with format:
```
{username: "testuser", password: "testuser"}
```



---
### Supplying JWT token

- After logged in, a jwt token for the logged in session would be returned
- You can use localstorage to store it (or cookies and other methods if you wish)
- Then you supply {headers: {Authorization: \`Bearer ${localStorage.id_token}\`}} in your requests
- A request example is below

---

### get all courses:
GET
https://xnor.space/api/courses

use the auth token like so:
axios.get("https://xnor.space/api/courses", {headers: {Authorization: \`Bearer ${res.data.id_token}\`}})

where `res` is the response of the login POST

---

### get all course types:

GET

https://xnor.space/api/courses/categories or https://xnor.space/api/courses/types


---

### User getting his own info:

GET
https://xnor.space/api/account


---

### instructor posting course:
https://xnor.space/api/courses/

POST
```
{
    "name": "lorem",
    "type": "ipsum",
    "starttime": "2021-04-29T14:58:46", (default unzoned,use 2021-03-29T00:00:00-05:00 for zoned)
    "duration": "PT86400S", (example: PT86400S for 1 day, PT1H43M20S for 1 hour 43 minutes 20 second)
    "intensity": 10, (integer 0-10)
    "location": "lorem",
    "maxsize": 20
}

```

---
### instructor course update endpoint:
https://xnor.space/api/courses/{id}

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

https://xnor.space/api/account/courses/{id}

#### Supports:

1. student add oneself to a course at this endpoint:

POST

2. student remove oneself to a course at this endpoint:

DELETE



---
### student/instructor obtaining their own courses (attending/teaching):

GET

https://xnor.space/api/account/courses

---
### instructor getting courses that they attend:

GET

https://xnor.space/api/account/courses/attending


---
### user modifying their account info

POST

https://xnor.space/api/account/


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

https://xnor.space/api/account/change-password

with body

```
{
    "currentPassword" : "currentpass",
    "newPassword": "newpass"
}

```



### Search
GET
https://xnor.space/api/courses/search?field=term&..


- all min max are containing (i.e. mindate = 2021-04-01 will return results containing courses that start on April first)
- type, name, location are all partial matches (i.e. whether the string contains the search query term)
- you can leave any of the search term out
- therefore GET https://xnor.space/api/courses/search = GET https://xnor.space/api/courses/


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

https://xnor.space/api/courses/search?mnt=00:00:00&mxt=23:59:59&mnd=1970-01-01&mxd=2100-12-31&mndr=PT0.000S&mxdr=PT24H&mni=0&mxi=10000&type=lorem&name=ipsum&loc=mars&nf=1&ins=testinstructor



---

### Commenting

#### Adding Comment

POST

https://xnor.space/api/courses/{courseid}/comments

with body:

```
{ title: "commenttitle",  bodytext: "dghsdkdfhgsdfghdfsghdfksv rhkn sfd fd "}

```

#### Getting all comments of a course

GET

https://xnor.space/api/courses/{courseid}/comments



#### Editing Comment

PATCH

https://xnor.space/api/courses/{courseid}/comments/{commentid}

with body:

```
{ bodytext: "edited"}

```

#### Deleting Comment

DELETE

https://xnor.space/api/comments/{commentid}

- Note that this endpoint doesnt have courses in the path



#### Getting all comments
- not sure why this might be used, but it's there

GET

https://xnor.space/api/comments
