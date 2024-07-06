package com.mobile.pft.reclamos.ui.activity

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.pft.reclamos.ui.screens.ReclamosScreen
import com.mobile.pft.reclamos.ui.adapters.ReclamosViewModel
import com.mobile.pft.reclamos.ui.components.AddIcon
import com.mobile.pft.reclamos.ui.components.ExitButton
import com.mobile.pft.reclamos.ui.components.ReclamosSearchBar
import com.mobile.pft.reclamos.ui.components.UtecIcon
import com.mobile.pft.reclamos.ui.screens.TopAppBar
import com.mobile.pft.utils.TipoUsuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReclamosApp(navController: NavHostController, nombreUsuario: String?, tipoUsuario: String?) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val reclamosViewModel: ReclamosViewModel =
        viewModel(factory = ReclamosViewModel.Factory)
    val searchFn: (String) -> Unit = if(nombreUsuario != null) {
        fun(keytext: String) = reclamosViewModel.getStudentReclamosBy(username = nombreUsuario, keytext = keytext)
    } else {
        reclamosViewModel::getReclamosByTitleLike
    }
    if(nombreUsuario != null) {
        reclamosViewModel.getReclamosBy(nombreUsuario)
    } else {
        reclamosViewModel.getReclamos()
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior, navIcon = { ExitButton() }, trailingIcon = { if(tipoUsuario?.uppercase() == TipoUsuario.ESTUDIANTE.toString()) AddIcon(context) else UtecIcon() }) },
        bottomBar = { ReclamosSearchBar(onQueryChange = searchFn, isSearchActive = false) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ReclamosScreen(
                uiState = reclamosViewModel.uiState,
                retryAction = {},
                contentPadding = paddingValues,
                controller = navController,
            )
        }
    }
}