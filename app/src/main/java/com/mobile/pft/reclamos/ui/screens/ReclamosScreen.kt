package com.mobile.pft.reclamos.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.ui.adapters.ReclamosUiState
import com.mobile.pft.reclamos.ui.components.ColoredTextField
import com.mobile.pft.utils.NamedObject

@Composable
fun ReclamosScreen(
    uiState: ReclamosUiState,
    retryAction: () -> Unit,
    contentPadding: PaddingValues,
    controller: NavHostController
) {
    when (uiState) {
        is ReclamosUiState.Loading -> LoadingScreen()
        is ReclamosUiState.Success -> ReclamosList(
            reclamos = uiState.reclamos,
            contentPadding = contentPadding,
            controller = controller,
        )
        is ReclamosUiState.Error -> ErrorScreen(retryAction)
    }
}

@Composable
fun ReclamosList(
    reclamos: List<ReclamoDTO>,
    contentPadding: PaddingValues,
    controller: NavHostController,
) {
    Log.i("ReclamosScreen","Reclamos utilizados: $reclamos")
    val state = rememberLazyListState()
    Column(
        modifier = Modifier.padding(contentPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "Reclamos",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                "Estado actual",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        LazyColumn(
            state = state
        ) {
            items(reclamos) {reclamo ->
                ReclamoItem(reclamo = reclamo, onClick = { controller.navigate("reclamo/${reclamo.idReclamo}") })
                Divider(color = Color.Gray, modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp))
            }
        }
    }

}

@Composable
fun ReclamoItem(
    reclamo: ReclamoDTO,
    onClick: () -> Unit
) {
    Column {
        ListItem(
            headlineContent = { Text(reclamo.titulo) },
            supportingContent = { Text(reclamo.estudiante.usuario.nombreUsuario) },
            trailingContent = {
                ColoredTextField(option = reclamo.statusReclamo)
            },
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp)
                .clickable { onClick() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownBox(
    option: NamedObject,
    items: List<NamedObject>,
    onValueChange: (String) -> Unit = {},
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        CompositionLocalProvider(
            LocalTextInputService provides null // hace que no se muestre el teclado al clickear en nuestros dropdown
        ) {
            OutlinedTextField(
                readOnly = true,
                value = option.nombre,
                onValueChange = onValueChange,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                label = {  },
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(25.dp)
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded }
        ) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it.nombre) },
                    onClick = {
                        onValueChange(it.nombre)
                        expanded = !expanded
                    }
                )
            }
        }
    }
}

@Composable
fun AttrInputText(
    label: String
) {
    var attr by rememberSaveable {
        mutableStateOf("")
    }
    OutlinedTextField(
        readOnly = false,
        value = attr,
        onValueChange = { attr = it },
        label = { Text(label) },
        modifier = Modifier
            .background(color = colorResource(id = android.R.color.white)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
        )
    )
}