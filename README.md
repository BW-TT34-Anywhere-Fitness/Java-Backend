# JWT Token implementation - Jiawei
##


login:

POST
http://xnor.space/api/authenticate

with format:
{username: "admin", password: "admin"}


---

courses:

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




# Java-Backend (Pat)



