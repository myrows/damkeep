package com.salesianostriana.dam.damkeep

import com.salesianostriana.dam.damkeep.user.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*
import javax.annotation.PostConstruct

interface NotaRepository : JpaRepository<Nota, UUID> {

    @Query("select n from Nota n where n.uuidUser = :uuidUser")
    fun findNotasByAuthor ( uuidUser : UUID? ) : List<Nota>

    @Query("select n from Nota n where n.title = :title")
    fun findNotasByTitle ( title : String ) : List<Nota>
}

@Component
class InitDataComponent( val notaRepository: NotaRepository, val userRepository: UserRepository, var encoder : PasswordEncoder) {

    @PostConstruct
    fun initData ( ) {

        var admin : User = User( "user", encoder.encode("12345678"), "Luis Miguel", null, "ADMIN" )
        var user : User = User( "admin", encoder.encode("12345678"), "Miguel Campos", null, "USER" )
        userRepository.save(user)

        var nota = Nota ( "Realizar la compra", "Hacer la compra en el mercadona", user)
        var nota1 = Nota ( "Hacer el proyecto elaborado por Luismi", "Está en el aula virtual junto a su vídeo de ejemplo", user)
        var nota2 = Nota ( "Hacer descanso a las 13:30", "Tengo mucha hambre", user)

        var nota3 = Nota ( "Realizar la compra", "Hacer la compra en el mercadona", admin)
        var nota4 = Nota ( "Hacer el proyecto elaborado por Luismi", "Está en el aula virtual junto a su vídeo de ejemplo", admin)
        var nota5 = Nota ( "Hacer descanso a las 13:30", "Tengo mucha hambre", admin)

        var notas = listOf<Nota>(nota, nota1, nota2, nota3, nota4, nota5)
        notaRepository.saveAll(notas)
    }
}