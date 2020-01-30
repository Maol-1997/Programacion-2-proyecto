# API

Obtener token:

    http://127.0.0.1:8080/api/authenticate POST
    {
    "password": "admin",
    "rememberMe": true,
    "username": "admin"
    }

Crear cliente:

    http://127.0.0.1:8080/api/cliente POST
    {
    "nombre": "Juan",
    "apellido": "Perez"
    }

Crear tarjeta

    http://127.0.0.1:8080/api/tarjeta POST
    {
    "nombre": "roberto",
    "apellido": "Perez",
    "vencimiento": "11/2019",
    "numero": 1234567891234567,
    "limite": 5000,
    "seguridad": 865,
    "cliente_id": 174
    }

Comprar:

    http://127.0.0.1:8080/api/comprar POST
    {
    "token": "d23905b862d28498a5c1efdf9834d700d03d4b3a67d0887503a8e109832e4125",
    "precio": 5000,
    "descripcion": "Play 4"
    }

Obtener clientes:

    http://127.0.0.1:8080/api/clientes GET

Obtener tarjetas:

    http://127.0.0.1:8080/api/tarjetas GET
