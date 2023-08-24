# Challenge
## Intrucciones
-Se adjuntan las colecciones para ser importadas en postman
### Ejecucion
Las apis estan dockerizadas, ejecutando el siguiente comando deberian levantar todos los microservicios
```bash
docker-compose up -d --force-recreate --build
```
### Datos
#### Datos del cliente
- Usuario=web_client
- Password=myClientSecretValue
#### Datos de usuarios
|Role | user |password |
|--- | --- | --- |
|Admin | admin | 654321 | 
|User | user1 | 654321 |
|User | user2 | 654321 |
### Obtencion del token
Para la autenticacion se implemento Authorization Code
 - Solicitar code a http://localhost:8030/oauth2/authorize?response_type=code&client_id=web_client&redirect_uri=http://127.0.0.1:80/authorized&scope=ROLE_ADMIN
 - Obtener el code del redireccionamiento
 - Con el codigo solicitar el token, por ejemplo con el siguiente curl
 ```bash
 curl --location --request POST 'http://localhost:8030/oauth2/token' \
--header 'Authorization: Basic d2ViX2NsaWVudDpteUNsaWVudFNlY3JldFZhbHVl' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: csrftoken=0o7DvflBJtWmk0JhgxN2joOAqL0kv4HQpWLgBU0Q1H8J8lo7hF6cRTwDzLBuctxK; JSESSIONID=D4D144A9023467C3122B6E5234EA4495' \
--data-urlencode 'code=dNw-NDLk8cz_XJNeBKGHH1n4gMzQ1vHMu00F-IVoXImg3fc6ft3zsbzbZ13iq7K05lbMkPCPQazkuatdZxNV6NYF_g0WNNK-QH3gbOpDH-Ch1_WzKZFVC-2aDJq6J8Xc' \
--data-urlencode 'redirect_uri=http://127.0.0.1:80/authorized' \
--data-urlencode 'grant_type=authorization_code'
```
 ### Consumir las apis
 #### Api chatgpt,carga respuestas
 Carga preguntas y respuestas, solo para rol admin, si utilizamos entre llaves el valor user, en la rta se completara con el nombre del usuario que solicita la respuesta.
 [POST] http://localhost:9090/chatgpt/admin/chat
  Body
 ```json
 {
    "question":"Hola",
    "answer":"Hola {user}"
}
 ```
 #### Api chatgpt, chat
 Se envia una pregunta y se devuelve una rta, valido para rol admin y user
 [POST] http://localhost:9090/chatgpt/chat
  Body
 ```json
 {
    "question":"hola"
}
 ```
 #### Api billing, get billig
 Devuelve la facturacion para un usuario
 [GET] http://localhost:9090/billing?user=<NAME_USER>
  Response example
 ```json
 {
    "status": "OK",
    "message": "Ok",
    "data": {
        "value": 800.0
    }
}
 ```
  #### Api billing, put value billing
 Modifica el valor de facturacion por query
 [PUT] http://localhost:9090/billing
  Body
 ```json
 {
    "value":200.0
}
 ```
----------------------------------------------------------
# Cambios feature/persistence-mepv

Para consultar los métodos DAO es necesario loguearse como admin, luego se debe generar data en la base de datos, ya sea creando preguntas y respuestas o haciendo
consultas a las preguntas existentes. En la base de datos hay dos preguntas registradas, se pueden ver cuales son con el endpoint 'GetAllResponse - Admin'
En la base de dato por defecto está guardado un usuario admin, con las mismas credenciales {user: admin, password: 654321}
Existen dos endpoint para registrar usuarios, uno permitido a todos, diseñado para usurios y otro protegido, en el cual sólo admins pueden registrar otros admin o users

#### API Register as User
Un usuario se puede registar en la app
[POST] http://localhost:9090/user/username
Body
```json
{
    "username":"mepv",
    "password": "12345",
    "firstName": "Mario",
    "lastName": "Palacios",
    "phoneNumber": "1111-111",
    "gender": "MASCULINO",
    "country": "Argentina"
}
```

#### API Create a user as Admin
Un administrador puede registrar nuevos usuarios en la app, ya sea user o admin. Es necesario especificar el rol, SCOPE_ROLE_ADMIN o SCOPE_ROLE_USER
[POST] http://localhost:9090/user/admin/username
Body
```json
{
    "username": "another-admin",
    "password": "1234",
    "firstName": "another",
    "lastName": "admin",
    "phoneNumber": "1111-111",
    "gender": "MASCULINO",
    "country": "Argentina",
    "role": {
        "name": "SCOPE_ROLE_ADMIN"
    }
}
```

#### API GetAllResponse - Admin 
Endpoint para consultar todas las preguntas
[GET] http://localhost:9090/chatgpt/admin/chat
Response example
```json
{
    "status": "OK",
    "message": "Ok",
    "data": [
        {
            "question": "¿Cuál es la capital de Argentina?",
            "answer": {
                "value": "La capital de Argentina es Buenos Aires, también conocida como Ciudad Autónoma de Buenos Aires"
            }
        },
        {
            "question": "¿Dónde queda Bariloche?",
            "answer": {
                "value": "San Carlos de Bariloche se encuentra en la región patagónica de Argentina, es una de las ciudades más atractivas de Argentina"
            }
        }
    ]
}
```

#### API Create Question Data Chatgpt - Admin
Endpoint para crear una pregunta con respuesta
[POST] http://localhost:9090/chatgpt/admin/chat
Body
```json
{
    "question":"¿Qué día es hoy?",
    "answer": {
        "value": "Hoy es viernes"
    }
}
```

#### API Chat Answer
Endpoint para obtener la respuesta a una pregunta
[GET] http://localhost:9090/chatgpt/chat
Body
```json
{
    "question":"¿Dónde queda Bariloche?"
}
```

# Métodos DAO

	- Lista de usuarios con un determinado rol

#### API Retrieve Users by Role - Admin
Endpoint para devolver lista de usuarios por rol, user o admin
[GET] http://localhost:9090/user/search/usernames?role=admin
param 'role'

	- Cantidad de veces que fue preguntada una pregunta

#### API Retrieve Question Time Asked - Admin
Endpoint para devolver la cantidad de veces que fue consultada una pregunta
[GET] http://localhost:9090/chatgpt/admin/data
Body
```json
{
    "question": "¿Dónde queda Bariloche?"
}
```

	- Lista de usuarios que realizaron preguntas y no tienen saldo deudor

#### API Retrieve Users without Debt - Admin
Endpoint para consultar los usuarios sin deuda
[GET] http://localhost:9090/user/admin/users-without-debt

	- Lista de usuarios Admin que dieron de alta preguntas y usuaron el sistema para realizar al menos alguna de la misma pregunta que crearon

#### API Retrieve Admins Asked Own Questions - Admin
Endpoint para consultar lista de admin que consultaron sus preguntas creadas
[GET] http://localhost:9090/user/admin/asked-own-questions

	- Saldo deudor de un determinado usuario

#### API Retrieve Billing By User - Admin
Endpoint para consultar el saldo deudor por usuario
[GET] http://localhost:9090/billing?user=admin

	- Lista de usuarios que realizaron preguntas en un rango de fechas

#### API Retrieve Users by Dates - Admin
Endpoint para consultar usuarios que realizaron preguntas por fechas
[GET] http://localhost:9090/user/admin/users-by-date?startDate=2023-08-17&endDate=2023-08-19
param 'startDate' 'endDate' en formato 'yyyy-MM-dd'

	- Lista de preguntas registradas que nunca fueron preguntas

#### API Retrieve Questions Not Asked - Admin
Endpoint que devuelve las preguntas no consultadas
[GET] http://localhost:9090/chatgpt/admin/data-not-asked
