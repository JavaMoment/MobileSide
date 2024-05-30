package com.mobile.pft.reclamos.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobile.pft.R
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.ui.activity.ReclamosApp
import com.mobile.pft.reclamos.ui.adapters.ReclamosUiState
import com.mobile.pft.utils.NamedObject

class ReclamosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                ReclamosApp()
            }
        }
    }
}

@Composable
fun AnalistaReclamosScreen(
    reclamosUiState: ReclamosUiState,
    status: List<StatusReclamoDTO>,
    retryAction: () -> Unit,
    contentPadding: PaddingValues
) {
    when (reclamosUiState) {
        is ReclamosUiState.Loading -> LoadingScreen()
        is ReclamosUiState.Success -> ReclamosList(
            reclamos = reclamosUiState.reclamos,
            status = status,
            contentPadding = contentPadding
        )
        is ReclamosUiState.Error -> ErrorScreen(retryAction)
    }
}

@Composable
fun ReclamosList(
    reclamos: List<ReclamoDTO>,
    status: List<StatusReclamoDTO>,
    contentPadding: PaddingValues
) {
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
                ReclamoItem(reclamo = reclamo, status)
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
    status: List<StatusReclamoDTO>
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
        )
    }
}

/**
 * Mientras conseguimos los datos para los dropdown mostramos una pantalla de carga.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Cargando..."
    )
}

/**
 * Si la api genera algún error, mostramos una pantalla que permita reintentar.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "Error de conexión con el servidor.", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Reintentar")
        }
    }
}

@Composable
fun ColoredTextField(
    option: NamedObject
) {
    val colors = if(option.nombre == "PENDIENTE") {
        OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.Gray,
            unfocusedContainerColor = Color.Gray
        )
    } else if(option.nombre == "EN PROGRESO") {
        OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.Yellow,
            unfocusedContainerColor = Color.Yellow
        )
    } else {
        OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.Red,
            unfocusedContainerColor = Color.Red
        )
    }
    OutlinedTextField(
        readOnly = true,
        value = option.nombre,
        onValueChange = {},
        label = {},
        modifier = Modifier
            .size(110.dp, 60.dp),
        colors = colors,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        shape = RoundedCornerShape(25.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownBox(
    option: NamedObject,
    items: List<NamedObject>,
    onValueChange: (String) -> Unit = {},
    onSelected: () -> Unit = {}
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
                    .size(130.dp, 70.dp)
                    .clickable { expanded = !expanded },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedContainerColor = Color.Yellow,
                    unfocusedContainerColor = Color.Yellow
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
                    onClick = onSelected
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