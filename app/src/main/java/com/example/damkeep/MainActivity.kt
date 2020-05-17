package com.example.damkeep

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Range
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import com.example.damkeep.api.DamkeepService
import com.example.damkeep.api.generator.ServiceGenerator
import com.example.damkeep.common.MyApp
import com.example.damkeep.response.NuevaNota
import com.example.damkeep.response.PostNotaResponse
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_custom_dialog.view.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var serviceGenerator : ServiceGenerator = ServiceGenerator()
        var service : DamkeepService = serviceGenerator.createServiceNota(DamkeepService::class.java)

        val dialog = AlertDialog.Builder(this)
        val dialogView  = layoutInflater.inflate(R.layout.activity_custom_dialog, null)
        val editTitle = dialogView.editTitleNota
        val editBody = dialogView.editBodyNota
        dialog.setView(dialogView)
        dialog.setCancelable(true)
        dialog.setPositiveButton("CREAR") { dialogInterface : DialogInterface, i: Int ->
            val customDialog = dialog.create()

                if ( !editTitle.text.isEmpty() && !editBody.text.isEmpty() ) {


                    var note : NuevaNota = NuevaNota ( editTitle.text.toString(), editBody.text.toString() )
                    var call : Call<PostNotaResponse> = service.createNote(note)

                    call.enqueue( object : retrofit2.Callback<PostNotaResponse> {
                        override fun onResponse(
                            call: Call<PostNotaResponse>,
                            response: Response<PostNotaResponse>
                        ) {
                            if ( response.isSuccessful ) {
                                Toast.makeText(this@MainActivity, "Has creado la nota con éxito", Toast.LENGTH_LONG).show()
                                customDialog.dismiss()
                            }
                        }

                        override fun onFailure(call: Call<PostNotaResponse>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                        }
                    })
                } else {
                    Toast.makeText(this, "No puedes guardar una nota vacía, ¿para qué te sirve? :(", Toast.LENGTH_LONG).show()
                }
        }


        var fab = fabAddNote

        fab.setOnClickListener {
            dialog.show()

        }

    }
}
