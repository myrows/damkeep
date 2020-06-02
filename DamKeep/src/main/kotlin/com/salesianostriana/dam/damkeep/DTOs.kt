package com.salesianostriana.dam.damkeep

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import org.apache.tomcat.jni.Local
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.time.LocalDate
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

data class NotaDTO(
        val title : String,
        var body : String,
        var timeCreated : LocalDate? = null,
        var lastUpdated : LocalDate? = null,
        val id : UUID? = null

)

fun Nota.toNotaDTO() = NotaDTO( title, body, timeCreated, lastUpdated, id )

fun NotaDTO.toNota() = Nota( title, body, null, timeCreated, lastUpdated, id )

data class NuevaNotaDTO(
        val title : String,
        val body : String,
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" )
        @DateTimeFormat(style = "yyyy-MM-dd")
        @CreatedDate
        val timeCreated : LocalDate? = null,
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" )
        @DateTimeFormat(style = "yyyy-MM-dd")
        @LastModifiedDate
        val lastUpdated: LocalDate? = null,
        @AuthenticationPrincipal
        val user : User? = null

)

fun NuevaNotaDTO.toNota() = Nota( title, body, user, timeCreated, lastUpdated )

data class UserDTO(
        var username : String,
        var fullName: String,
        var roles: String,
        val id: UUID? = null
)

fun User.toUserDTO() = UserDTO(username, fullName, roles.joinToString(), id)

data class CreateUserDTO(
        var username: String,
        var fullName: String,
        val password: String,
        val password2: String
)