package com.mobile.pft

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobile.pft.reclamos.ui.activity.ReclamoApp
import com.mobile.pft.reclamos.ui.activity.ReclamosApp

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa SharedPreferences
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("userLogged", null)
        Log.d("MainActivity", "Username utilizado: $username")

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                NavigationComponent(navController, username)
            }
        }

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
fun NavigationComponent(navController: NavHostController, username: String?) {
    NavHost(
        navController = navController,
        startDestination = "catwalk"
    ) {
        composable(
            route = "catwalk",
        ) {
            Log.i("Pasarela", "navegando hacia pantalla inicial")
            navController.navigate("reclamos/$username")
        } // Fix para poder ir a reclamos de manera parametrizada y además agrega la imposibilidad de ir al login navegando hacia atras ;)
        composable(
            route = "reclamos/{nombreUsuario}",
            arguments = listOf(
                navArgument("nombreUsuario") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {entry ->
            val nombreUsuario: String? = entry.arguments?.getString("nombreUsuario")
            Log.i("Navigation", "nombre de usuario: $nombreUsuario")
            ReclamosApp(navController, nombreUsuario)
        }
        composable("reclamo/{reclamoId}") { entry ->
            val reclamoId = entry.arguments?.getString("reclamoId")?.toLong()
            Log.i("Navigation", "reclamo id: $reclamoId")
            ReclamoApp(navController, reclamoId)
        }
    }
}