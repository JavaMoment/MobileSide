package com.mobile.pft.reclamos.ui.activity

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.pft.reclamos.ui.adapters.ReclamoViewModel
import com.mobile.pft.reclamos.ui.components.GoBackButton
import com.mobile.pft.reclamos.ui.screens.ReclamoScreen
import com.mobile.pft.reclamos.ui.screens.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReclamoApp(navController: NavHostController, reclamoId: Long?) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior, navIcon = { GoBackButton(
            onClick = {
                navController.navigate("reclamos/${navController.previousBackStackEntry?.arguments?.getString("tipoUsuario")}/${navController.previousBackStackEntry?.arguments?.getString("nombreUsuario")}")
            }
        ) }) },
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val reclamoViewModel: ReclamoViewModel =
                viewModel(factory = ReclamoViewModel.Factory)
            if (reclamoId != null) {
                reclamoViewModel.getReclamo(reclamoId)
            }
            ReclamoScreen(
                reclamoUiState = reclamoViewModel.uiState,
                status = reclamoViewModel.statusReclamo,
                contentPadding = paddingValues,
                isAnalista = navController.previousBackStackEntry?.arguments?.getString("nombreUsuario") == null, // es analista si los reclamos no se filtran por nombre de usuario
                eventos = reclamoViewModel.eventos,
                reclamoViewModel = reclamoViewModel
            )
        }
    }
}