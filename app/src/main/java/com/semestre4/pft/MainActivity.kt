package com.semestre4.pft

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.semestre4.pft.R

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa SharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        // Recupera el token JWT
        val token = sharedPreferences.getString("jwt_token", null)

        // Usa el token según sea necesario
        if (token != null) {
            // Token está disponible, puedes usarlo para autenticar futuras solicitudes
            // ...
        } else {
            // Token no está disponible, manejar según sea necesario
            // ...
        }
    }
}
