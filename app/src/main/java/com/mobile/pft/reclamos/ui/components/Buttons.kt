package com.mobile.pft.reclamos.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun MenuButton() {
    IconButton(onClick = { /* do something */ }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "ContextMenuList"
        )
    }
}

@Composable
fun GoBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "GoBackBtn"
        )
    }
}