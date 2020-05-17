package com.example.damkeep.notas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.damkeep.R
import com.example.damkeep.viewmodel.NotaViewModel

class NotaFragment : Fragment() {

    private lateinit var notaAdapter : MyNotaRecyclerViewAdapter
    private lateinit var notaViewModel : NotaViewModel
    private var columnCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nota_list, container, false)

        notaViewModel = ViewModelProvider(this).get(NotaViewModel::class.java)
        notaAdapter = MyNotaRecyclerViewAdapter()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = notaAdapter
            }
        }
        notaViewModel.getAllNotas().observe(viewLifecycleOwner, Observer {
            notaAdapter.setData(it)
        })
        return view
    }
}
