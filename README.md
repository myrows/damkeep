# Damkeep - Aplicación móvil para la gestión de notas rápidas

Damkeep forma parte de un proyecto como práctica del lenguaje Kotlin en Android Studio y Spring Boot

- Aplicación móvil en Android - Kotlin
- API REST - Spring Boot Kotlin

### Tecnologías

* Android Studio
* IntelliJ IDEA
* JWT
* Spring Boot

### Lenguaje

* Kotlin

### Dependencias

* Spring Data JPA
* Spring Web
* Spring Security
* H2 Database

### Arquitectura

* MVVM

## ¿Cómo probarlo en Postman?

```
POST localhost:9000/user/

Body

{
	"username" : "example",
	"fullName" : "My example",
	"password" : "12345678",
	"password2" : "12345678"
}
```

```
POST localhost:9000/auth/login

Body

{
	"username" : "user",
	"password" : "12345678"
}
```

```
GET localhost:9000/user/me

Authorization : Bearer Token

```

```
GET localhost:9000/notas/

Authorization : Bearer Token

```

```
GET localhost:9000/notas/title/{title}

Authorization : Bearer Token

```

```
GET localhost:9000/notas/author/{idUser}

Authorization : Bearer Token

```

```
PUT localhost:9000/notas/{idNota}

Authorization : Bearer Token

Body

{
	"title" : "Test",
	"body" : "Test"
}

```

```
DELETE localhost:9000/notas/{idNota}

Authorization : Bearer Token

```
