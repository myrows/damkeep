package com.example.damkeep.api

import com.example.damkeep.Register
import com.example.damkeep.RegisterResponse
import com.example.damkeep.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface DamkeepService {

    @GET("/notas/author/")
    fun findAllNotasByAuthor() : Call<NotaResponse>

    @GET("/notas/")
    fun findAllNotas() : Call<NotaResponse>

    @GET("/notas/title/{title}")
    fun findAllByTitle ( @Path("title") title : String ) : Call<NotaResponse>

    @GET("/notas/{id}")
    fun findAllById ( @Path("id") id : String ) : Call<NotaResponse>

    @GET("/notas/author/{author}")
    fun findAllByAuthor ( @Path("author") author : String ) : Call<NotaResponse>

    @POST("/auth/login")
    fun loginUser( @Body login : Login ) : Call<LoginResponse>

    @POST("/user/")
    fun register( @Body register : Register ) : Call<RegisterResponse>

    @POST("/notas/")
    fun createNote( @Body note : NuevaNota ) : Call<PostNotaResponse>

    @PUT("/notas/{id}")
    fun editNote( @Path("id") id : String, @Body note : NuevaNota ) : Call<PostNotaResponse>

    @DELETE("/notas/{id}")
    fun deleteNote( @Path("id") id : String ) : Call<ResponseBody>
}