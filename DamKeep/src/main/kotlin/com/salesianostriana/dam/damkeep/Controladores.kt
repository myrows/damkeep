package com.salesianostriana.dam.damkeep

import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/notas")
class NotaController ( val notaRepository: NotaRepository ) {

    @GetMapping("/")
    fun findAll() = notaRepository.findAll().map { it.toNotaDTO() }

    @GetMapping("/author/")
    fun findNotaByAuthor( @AuthenticationPrincipal user : User ) = notaRepository.findNotasByAuthor( user ).map { it.toNotaDTO() }

    @GetMapping("/title/{title}")
    fun findNotaByTitle( @PathVariable title : String ) = notaRepository.findNotasByTitle( title ).map { it.toNotaDTO() }

    @GetMapping("/{id}")
    fun findNotaByTitle( @PathVariable id : UUID ) = notaRepository.findNotasById( id ).map { it.toNotaDTO() }


    @PostMapping("/")
    fun createNota ( @RequestBody nota : NuevaNotaDTO, @AuthenticationPrincipal user: User ) : Nota {
        return notaRepository.save(NuevaNotaDTO( nota.title, nota.body, nota.timeCreated, nota.lastUpdated, user ).toNota())
    }


    @PutMapping("/{id}")
    fun updateNota( @RequestBody nota : NuevaNotaDTO, @PathVariable id : UUID ) : NotaDTO {

        return notaRepository.findById( id ).map { it ->
            val notaUpdated : Nota = it.copy( title = nota.title, body = nota.body,
            timeCreated = nota.timeCreated, lastUpdated = nota.lastUpdated, user = null)

            notaRepository.save(notaUpdated).toNotaDTO()
        }.orElseThrow() {
            ResponseStatusException( HttpStatus.NOT_FOUND, "No se ha encontrado resultados" )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteNota( @PathVariable id : UUID ): ResponseEntity<Void> {
        notaRepository.deleteById( id )
        return ResponseEntity.noContent().build()
    }




}