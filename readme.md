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
Role | user |password
--- | --- | --- 
Admin | admin | 654321 
User | user1 | 654321
User | user2 | 654321
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
 