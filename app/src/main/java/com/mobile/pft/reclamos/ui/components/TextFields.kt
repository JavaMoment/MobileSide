package com.mobile.pft.reclamos.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobile.pft.utils.NamedObject
import com.mobile.pft.utils.Status


@Composable
fun ColoredTextField(
    option: NamedObject,
    modifier: Modifier = Modifier.size(110.dp, 60.dp)
) {
    val colors = if(option.nombre == Status.PENDIENTE.value) {
        OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.Gray,
            unfocusedContainerColor = Color.Gray
        )
    } else if(option.nombre == Status.EN_PROGRESO.value) {
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
    CompositionLocalProvider(
        LocalTextInputService provides null // hace que no se muestre el teclado
    ) {
        OutlinedTextField(
            readOnly = true,
            value = option.nombre,
            onValueChange = {},
            label = {},
            modifier = modifier,
            colors = colors,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            shape = RoundedCornerShape(25.dp)
        )
    }
}


@Composable
fun AttributeInputText(
    fieldLabel: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = fieldLabel,
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.height(5.dp))
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
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