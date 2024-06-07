package com.mobile.pft

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.pft.R
import com.mobile.pft.reclamos.ui.activity.ReclamoApp
import com.mobile.pft.reclamos.ui.activity.ReclamosApp

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                NavigationComponent(navController)
            }
        }
        //setContentView(R.layout.activity_main)

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

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "reclamos"
    ) {
        composable("reclamos") {
            ReclamosApp(navController)
        }
        composable("reclamo/{reclamoId}") { entry ->
            val reclamoId = entry.arguments?.getString("reclamoId")?.toLong()
            Log.i("Navigation", "reclamo id: $reclamoId")
            ReclamoApp(navController, reclamoId)
        }
    }
}