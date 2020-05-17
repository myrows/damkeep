package com.example.damkeep.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.damkeep.repository.DamkeepRepository
import com.example.damkeep.response.Nota

class NotaViewModel : ViewModel() {

    var damkeepRepository : DamkeepRepository
    var notas : LiveData<List<Nota>>

    init {
        damkeepRepository = DamkeepRepository()
        notas = damkeepRepository.getAllNotas()
    }

    fun getAllNotas() : LiveData<List<Nota>> {
        return notas
    }
}