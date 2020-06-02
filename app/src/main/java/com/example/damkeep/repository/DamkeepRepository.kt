package com.example.damkeep.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.damkeep.api.DamkeepClient
import com.example.damkeep.api.DamkeepService
import com.example.damkeep.api.generator.ServiceGenerator
import com.example.damkeep.common.MyApp
import com.example.damkeep.common.SharedPreferencesManager
import com.example.damkeep.response.Nota
import com.example.damkeep.response.NotaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DamkeepRepository {

    var damkeepService : DamkeepService
    var notas : LiveData<List<Nota>>
    var serviceGenerator : ServiceGenerator = ServiceGenerator()
    var userId = SharedPreferencesManager.SharedPreferencesManager.getSomeStringValue("userId").toString()

    init {
        damkeepService = serviceGenerator.createServiceNota(DamkeepService::class.java)
        notas = getAllNotas()
    }

    fun getAllNotas() : LiveData<List<Nota>> {
        var dataNotas : MutableLiveData<List<Nota>> = MutableLiveData()
        val call: Call<NotaResponse> = damkeepService.findAllNotasByAuthor()
        call.enqueue(object : Callback<NotaResponse> {
            override fun onResponse(call: Call<NotaResponse>, response: Response<NotaResponse>) {
                if( response.isSuccessful ) {
                    dataNotas.value = response.body()
                }
            }
            override fun onFailure(call: Call<NotaResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })

        return dataNotas
    }

}