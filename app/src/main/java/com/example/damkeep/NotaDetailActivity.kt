package com.example.damkeep

import android.app.Service
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.damkeep.api.DamkeepService
import com.example.damkeep.api.generator.ServiceGenerator
import com.example.damkeep.common.SharedPreferencesManager
import com.example.damkeep.response.NotaResponse
import com.example.damkeep.response.NuevaNota
import com.example.damkeep.response.PostNotaResponse
import kotlinx.android.synthetic.main.activity_custom_dialog.view.*
import kotlinx.android.synthetic.main.activity_nota_detail.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NotaDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.damkeep.R.layout.activity_nota_detail)

        var title = titleNotaDetail
        var body = bodyNoteDetail
        var modeEdit = editNotaPut
        var deleteNota = deleteNota
        var serviceGenerator : ServiceGenerator = ServiceGenerator()
        var service : DamkeepService = serviceGenerator.createServiceNota(DamkeepService::class.java)

        // Edit note

        val dialog = AlertDialog.Builder(this)
        val dialogView  = layoutInflater.inflate(R.layout.activity_custom_dialog, null)
        val editTitle : EditText = dialogView.editTitleNota
        val editBody : EditText = dialogView.editBodyNota
        dialog.setView(dialogView)
        dialog.setCancelable(true)

        editTitle.setText(intent.extras!!.get("title").toString())
        editBody.setText(intent.extras!!.get("body").toString())
        var noteId = intent.extras!!.get("noteId").toString()

        dialog.setPositiveButton("ACEPTAR") { dialogInterface : DialogInterface, i: Int ->
            val customDialog = dialog.create()

            if ( !editTitle.text.isEmpty() && !editBody.text.isEmpty() ) {

                var note : NuevaNota = NuevaNota ( editTitle.text.toString(), editBody.text.toString() )
                var call : Call<PostNotaResponse> = service.editNote(noteId, note)

                call.enqueue( object : retrofit2.Callback<PostNotaResponse> {
                    override fun onResponse(
                        call: Call<PostNotaResponse>,
                        response: Response<PostNotaResponse>
                    ) {
                        if ( response.isSuccessful ) {
                            Toast.makeText(this@NotaDetailActivity, "Has editado la nota con éxito", Toast.LENGTH_LONG).show()
                            customDialog.dismiss()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<PostNotaResponse>, t: Throwable) {
                        Toast.makeText(this@NotaDetailActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        deleteNota.setOnClickListener {
            var call : Call<ResponseBody> = service.deleteNote( noteId )

            call.enqueue( object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if ( response.isSuccessful ) {
                        Toast.makeText(this@NotaDetailActivity, "Has eliminado la nota con éxito", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@NotaDetailActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                }
            })
        }

        modeEdit.setOnClickListener {
            dialog.show()
        }


        var call : Call<NotaResponse> = service.findAllByTitle(intent.extras!!.get("title").toString())

        call.enqueue( object : Callback<NotaResponse> {
            override fun onResponse(call: Call<NotaResponse>, response: Response<NotaResponse>) {
                if ( response.isSuccessful ) {
                    var myNota = response.body()!![0]

                    title.text = myNota.title
                    body.text = myNota.body
                }
            }

            override fun onFailure(call: Call<NotaResponse>, t: Throwable) {
                Toast.makeText(this@NotaDetailActivity, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })


    }
}
