package com.mobile.pft.reclamos.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mobile.pft.R
import com.mobile.pft.UtecApplication
import com.mobile.pft.reclamos.ui.screens.ReclamosScreen
import com.mobile.pft.reclamos.ui.adapters.ReclamosViewModel
import com.mobile.pft.reclamos.ui.components.MenuButton
import com.mobile.pft.reclamos.ui.screens.TopAppBar
import com.mobile.pft.utils.TipoUsuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupBottomBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        Text("Registrarme")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Black
                    )
                )
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReclamosApp(navController: NavHostController, nombreUsuario: String?) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior = scrollBehavior, navIcon = { MenuButton() }) },
        //bottomBar = { SignupBottomBar(scrollBehavior = scrollBehavior) }
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