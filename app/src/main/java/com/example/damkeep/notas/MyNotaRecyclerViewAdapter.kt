package com.example.damkeep.notas

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.damkeep.NotaDetailActivity
import com.example.damkeep.R
import com.example.damkeep.response.Nota

import kotlinx.android.synthetic.main.fragment_nota.view.*

class MyNotaRecyclerViewAdapter(
) : RecyclerView.Adapter<MyNotaRecyclerViewAdapter.ViewHolder>() {

    private var notas : List<Nota> = ArrayList<Nota>()
    lateinit var ctx : Context
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Nota

            var intent = Intent(ctx, NotaDetailActivity::class.java)
            intent.putExtra("title", "${item.title}")
            intent.putExtra("body", "${item.body}")
            intent.putExtra("noteId", "${item.id}")
            ctx.startActivity(intent)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_nota, parent, false)

        ctx = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notas[position]

        holder.nTitle.text = item.title
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = notas.size

    fun setData( notasData : List<Nota> ) {
        notas = notasData

        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val nTitle : TextView = mView.titleNote
    }
}
