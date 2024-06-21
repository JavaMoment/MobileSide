package com.mobile.pft.reclamos.ui.activity

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.pft.reclamos.ui.screens.ReclamosScreen
import com.mobile.pft.reclamos.ui.adapters.ReclamosViewModel
import com.mobile.pft.reclamos.ui.components.MenuButton
import com.mobile.pft.reclamos.ui.screens.TopAppBar
import com.mobile.pft.utils.TipoUsuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReclamoBottomBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    BottomAppBar(
        actions = {
            IconButton(onClick = {
                context.startActivity(Intent(context, AddClaimActivity::class.java))
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Pantalla de añadir un reclamo")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReclamosApp(navController: NavHostController, nombreUsuario: String?, tipoUsuario: String?) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior, navIcon = { MenuButton() }) },
        bottomBar = { if(tipoUsuario?.uppercase() == TipoUsuario.ESTUDIANTE.toString()) ReclamoBottomBar(scrollBehavior = scrollBehavior)}
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val reclamosViewModel: ReclamosViewModel =
                viewModel(factory = ReclamosViewModel.Factory)
            if(nombreUsuario != null) {
                reclamosViewModel.getReclamosBy(nombreUsuario)
            } else {
                reclamosViewModel.getReclamos()
            }
            ReclamosScreen(
                uiState = reclamosViewModel.uiState,
                retryAction = {},
                contentPadding = paddingValues,
                controller = navController,
            )
        }
    }
}