package com.mobile.pft.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.pft.MainActivity
import com.mobile.pft.R
import com.mobile.pft.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    // declara las variables para los componentes de la interfaz de usuario y sharedPreferences
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var sharedPreferences: SharedPreferences

    // método que se ejecuta cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // inicializa los componentes de la interfaz de usuario
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        // inicializa sharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        // establece el listener para el botón de login
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            login(email, password)
        }
    }

    // función para manejar el proceso de login
    private fun login(email: String, password: String) {
        // muestra la barra de progreso
        progressBar.visibility = View.VISIBLE

        // obtiene la instancia de retrofit
        val retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/")  // usar 10.0.2.2 en lugar de localhost
        val apiService = retrofit.create(ApiService::class.java)

        // crea una instancia de LoginRequest con el email y la contraseña
        val loginRequest = LoginRequest(email, password)

        // inicia una corrutina en el contexto de IO
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // hace la solicitud de login y obtiene la respuesta
                val response = apiService.doLogin(loginRequest).execute()
                runOnUiThread {
                    // oculta la barra de progreso
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        Toast.makeText(this@LoginActivity, loginResponse?.message, Toast.LENGTH_SHORT).show()

                        // almacena el token JWT en sharedPreferences
                        val editor = sharedPreferences.edit()
                        editor.putString("jwt_token", loginResponse?.token)
                        editor.apply()

                        // redirige a MainActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    // oculta la barra de progreso y muestra un mensaje de error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "error de aplicación: ${e.message}", Toast.LENGTH_SHORT).show()
                    println(e.message)
                }
            }
        }
    }
}
