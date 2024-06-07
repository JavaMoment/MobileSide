package com.mobile.pft.reclamos.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobile.pft.model.EventoDTO
import com.mobile.pft.model.PatchDTO
import com.mobile.pft.model.ReclamoDTO
import com.mobile.pft.model.StatusReclamoDTO
import com.mobile.pft.reclamos.ui.adapters.ReclamoUiState
import com.mobile.pft.reclamos.ui.adapters.ReclamoViewModel
import com.mobile.pft.reclamos.ui.components.AttributeInputText
import com.mobile.pft.reclamos.ui.components.ColoredTextField

@Composable
fun ReclamoScreen(
    reclamoUiState: ReclamoUiState,
    status: List<StatusReclamoDTO>,
    eventos: List<EventoDTO>,
    reclamoViewModel: ReclamoViewModel,
    contentPadding: PaddingValues,
    isAnalista: Boolean,
) {
    when (reclamoUiState) {
        is ReclamoUiState.Loading -> LoadingScreen()
        is ReclamoUiState.Success  -> if(isAnalista) AnalistaReclamoDetails(
            reclamo = reclamoUiState.reclamo,
            status = status,
            contentPadding = contentPadding,
            onDetailUpdate = reclamoViewModel::partialUpdate
        ) else EstudianteReclamoDetails(
            reclamo = reclamoUiState.reclamo,
            contentPadding = contentPadding,
            eventos = eventos,
        )
        is ReclamoUiState.Error -> ErrorScreen({})
    }
}

@Composable
fun AnalistaReclamoDetails(
    reclamo: ReclamoDTO,
    status: List<StatusReclamoDTO>,
    contentPadding: PaddingValues,
    onDetailUpdate: (Long, PatchDTO) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(contentPadding),
    ) {
        val scrollState = rememberScrollState()
        val toastContext = LocalContext.current
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
                onValueChange = {
                    statusReclamo = status.first { s -> s.nombre == it }
                    onDetailUpdate(
                        reclamo.idReclamo,
                        PatchDTO("replace", "/statusReclamo/idStatus", status.first { s -> s.nombre == it }.idStatus.toString())
                    )
                    Toast.makeText(toastContext, "El status del reclamo ha sido correctamente actualizado a: $status", Toast.LENGTH_SHORT).show()
                }
            )
            AttributeInputText(
                fieldLabel = "Detalle:",
                value = detalle,
                readOnly = false,
                onValueChange = {
                    detalle = it
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Guardar el detalle!",
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    onDetailUpdate(
                                        reclamo.idReclamo,
                                        PatchDTO("replace", "/detalle", detalle)
                                    )
                                    Toast.makeText(toastContext, "El detalle del reclamo ha sido correctamente actualizado!", Toast.LENGTH_SHORT).show()
                                }
                            ),
                        tint = Color.Green
                    )
                }
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
                value = reclamo.evento.nombre
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
fun EstudianteReclamoDetails(
    reclamo: ReclamoDTO,
    contentPadding: PaddingValues,
    eventos: List<EventoDTO>,
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
            var evento by remember { mutableStateOf(reclamo.evento) }
            var titulo by remember { mutableStateOf(reclamo.titulo) }
            var descripcion by remember { mutableStateOf(reclamo.descripcion) }
            var creditos by remember { mutableIntStateOf(reclamo.creditos?: 0) }
            var semestre by remember { mutableIntStateOf(reclamo.semestre?: 0) }

            // Attrs con nullability
            val detalle = reclamo.detalle?: ""

            AttributeInputText(
                fieldLabel = "Nombre de usuario:",
                value = reclamo.estudiante.usuario.nombreUsuario
            )
            AttributeInputText(
                fieldLabel = "Titulo:",
                value = titulo,
                leadingIcon = { TitleIcon() },
                readOnly = false,
                onValueChange = { titulo = it }
            )
            AttributeInputText(
                fieldLabel = "Descripción:",
                value = descripcion,
                readOnly = false,
                onValueChange = { descripcion = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status:",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            ColoredTextField(option = reclamo.statusReclamo, modifier = Modifier.fillMaxWidth())
            AttributeInputText(
                fieldLabel = "Detalle:",
                value = detalle,
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
            DropdownBox(
                option = evento,
                items = eventos,
                onValueChange = {evento = eventos.first { e -> e.nombre == it }}
            )
            AttributeInputText(
                fieldLabel = "Creditos:",
                value = creditos.toString(),
                readOnly = false,
                onValueChange = { creditos = it.toInt() }
            )
            AttributeInputText(
                fieldLabel = "Semestre:",
                value = semestre.toString(),
                readOnly = false,
                onValueChange = { semestre = it.toInt() }
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