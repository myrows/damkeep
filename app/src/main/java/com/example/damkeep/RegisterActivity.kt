package com.example.damkeep

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.damkeep.api.DamkeepService
import com.example.damkeep.api.generator.ServiceGenerator
import com.example.damkeep.response.Login
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.damkeep.R.layout.activity_register)

        var btnRegister: Button? = buttonRegister
        var edtName: EditText? = editTextUsernameR
        var edtEmail:EditText? = editTextEmailR
        var edtPassword:EditText? = editTextPasswordR
        var edtConfPassword:EditText? = editTextConfPasswordR
        var inSesion : TextView = textViewIniciarSesion
        lateinit var service: DamkeepService
        var serviceGenerator : ServiceGenerator = ServiceGenerator()
        service = serviceGenerator.createServiceRegister(DamkeepService::class.java)


        btnRegister?.setOnClickListener {

            if( !edtName!!.text.isEmpty() && !edtEmail!!.text.isEmpty() && !edtPassword!!.text.isEmpty() && !edtConfPassword!!.text.isEmpty() ) {

                var register : Register = Register( edtName.text.toString(), edtEmail.text.toString(), edtPassword.text.toString(), edtConfPassword.text.toString() )

                var call : Call<RegisterResponse> = service.register(register)

                call.enqueue( object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {

                        if( response.isSuccessful ) {
                            var intent : Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this@RegisterActivity, "Te has registrado correctamente", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                        Toast.makeText(this@RegisterActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
