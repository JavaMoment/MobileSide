package com.mobile.pft.reclamos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.ui.adapters.ReclamoUiState
import com.mobile.pft.reclamos.ui.components.AttributeInputText

@Composable
fun ReclamoScreen(
    reclamoUiState: ReclamoUiState,
    status: List<StatusReclamoDTO>,
    retryAction: () -> Unit,
    contentPadding: PaddingValues,
    isAnalista: Boolean
) {
    when (reclamoUiState) {
        is ReclamoUiState.Loading -> LoadingScreen()
        is ReclamoUiState.Success  -> if(isAnalista) ReclamoDetails(
            reclamo = reclamoUiState.reclamo,
            status = status,
            contentPadding = contentPadding,
        ) else LoadingScreen()
        is ReclamoUiState.Error -> ErrorScreen(retryAction)
    }
}

@Composable
fun ReclamoDetails(
    reclamo: ReclamoDTO,
    status: List<StatusReclamoDTO>,
    contentPadding: PaddingValues,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(contentPadding),
    ) {
        val scrollState = rememberScrollState()
        Text(
            "Reclamo #${reclamo.idReclamo}",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(5.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.verticalScroll(scrollState),
        ) {
            var statusReclamo by remember { mutableStateOf(reclamo.statusReclamo) }
            var detalle by remember { mutableStateOf(reclamo.detalle ?: "") }

            // Attrs con nullability
            val creditos = reclamo.creditos?: "No aplica"
            val semestre = reclamo.semestre?: "No aplica"

            AttributeInputText(
                fieldLabel = "Nombre de usuario:",
                value = reclamo.estudiante.usuario.nombreUsuario
            )
            AttributeInputText(
                fieldLabel = "Titulo:",
                value = reclamo.titulo,
                leadingIcon = { TitleIcon() },
            )
            AttributeInputText(
                fieldLabel = "Descripción:",
                value = reclamo.descripcion,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status:",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            DropdownBox(
                option = statusReclamo,
                items = status,
                onValueChange = {statusReclamo = status.first { s -> s.nombre == it }}
            )
            AttributeInputText(
                fieldLabel = "Detalle:",
                value = detalle,
                readOnly = false,
                onValueChange = {detalle = it}
            )
            AttributeInputText(
                fieldLabel = "Analista a cargo:",
                value = reclamo.analista.usuario.nombreUsuario,
            )
            AttributeInputText(
                fieldLabel = "Ultima modificación:",
                value = reclamo.modifDate.toString()
            )
            AttributeInputText(
                fieldLabel = "Fecha de creación:",
                value = reclamo.auditDate.toString()
            )
            AttributeInputText(
                fieldLabel = "Evento asociado:",
                value = reclamo.evento.titulo
            )
            AttributeInputText(
                fieldLabel = "Creditos:",
                value = creditos.toString()
            )
            AttributeInputText(
                fieldLabel = "Semestre:",
                value = semestre.toString()
            )
        }
    }
}

@Composable
fun TitleIcon() {
    Icon(
        Icons.Rounded.Info,
        contentDescription = ""
    )
}