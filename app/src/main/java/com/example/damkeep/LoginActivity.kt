package com.example.damkeep

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ebanx.swipebtn.OnStateChangeListener
import com.ebanx.swipebtn.SwipeButton
import com.example.damkeep.api.DamkeepClient
import com.example.damkeep.api.DamkeepService
import com.example.damkeep.api.generator.ServiceGenerator
import com.example.damkeep.common.MyApp
import com.example.damkeep.common.SharedPreferencesManager
import com.example.damkeep.response.Login
import com.example.damkeep.response.LoginResponse
import kotlinx.android.synthetic.main.activity_login2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        var edtEmail: EditText = editTextEmail
        var edtPassword:EditText? = editTextPassword
        var token: String? = null
        val swipeButton: SwipeButton? = swipe_btn
        var txtRegister: TextView? = textViewRegistrarse
        var damkeepClient : DamkeepClient = DamkeepClient()
        var service: DamkeepService
        var serviceGenerator : ServiceGenerator = ServiceGenerator()

        txtRegister?.setOnClickListener {
            var intent : Intent = Intent( this@LoginActivity, RegisterActivity::class.java )
            startActivity(intent)
        }

        swipeButton?.setOnStateChangeListener(OnStateChangeListener {
            active: Boolean ->

            if ( active ) {
                if (!edtEmail.text.toString().isEmpty() && !edtPassword?.text.toString().isEmpty()){
                    if(token == null){
                        service = serviceGenerator.createServiceLogin(DamkeepService::class.java)

                        var login : Login = Login(edtEmail.text.toString(), edtPassword?.text.toString());

                        var call : Call<LoginResponse> = service.loginUser(login)
                        call.enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                if ( response.isSuccessful ) {
                                    var intent : Intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    Toast.makeText(MyApp.instance, "Bienvendo, ${response.body()?.user?.fullName}", Toast.LENGTH_LONG).show()

                                    SharedPreferencesManager.SharedPreferencesManager.setSomeStringValue("tokenId", response.body()!!.token);
                                    SharedPreferencesManager.SharedPreferencesManager.setSomeStringValue("userId", response.body()!!.user.id);
                                } else {
                                    Toast.makeText(MyApp.instance, "Email y/o contraseña incorrecta", Toast.LENGTH_LONG).show()
                                }
                            }
                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Toast.makeText(MyApp.instance, "Error de conexión", Toast.LENGTH_LONG)
                            }
                        })
                    }else{
                        var goMain : Intent = Intent(this, MainActivity::class.java);
                        startActivity(goMain);
                    }

                }else{
                    Toast.makeText(this, "Uno de los campos está sin rellenar", Toast.LENGTH_SHORT).show();
                }
            }else{

            }
        })
    }
}
