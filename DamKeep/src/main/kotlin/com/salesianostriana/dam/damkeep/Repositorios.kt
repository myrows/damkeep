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

    @Query("select n from Nota n where n.user = :userFound")
    fun findNotasByAuthor ( userFound : User ) : List<Nota>

    @Query("select n from Nota n where n.title = :title")
    fun findNotasByTitle ( title : String ) : List<Nota>

    @Query("select n from Nota n where n.id = :idFound")
    fun findNotasById ( idFound : UUID ) : List<Nota>
}

@Component
class InitDataComponent( val notaRepository: NotaRepository, val userRepository: UserRepository, var encoder : PasswordEncoder) {

    @PostConstruct
    fun initData ( ) {

        var user : User = User( "user", encoder.encode("12345678"), "Luis Miguel", null, "USER" )
        userRepository.save(user)

        var nota = Nota ( "Realizar la compra", "Hacer la compra en el mercadona", user = user)
        var nota1 = Nota ( "Hacer el proyecto elaborado por Luismi", "Está en el aula virtual junto a su vídeo de ejemplo", user = user)
        var nota2 = Nota ( "Hacer descanso a las 13:30", "Tengo mucha hambre", user = user)

        var notas = listOf<Nota>(nota, nota1, nota2)
        notaRepository.saveAll(notas)
    }
}