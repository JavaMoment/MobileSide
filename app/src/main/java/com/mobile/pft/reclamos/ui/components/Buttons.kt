package com.mobile.pft.reclamos.ui.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobile.pft.R
import com.mobile.pft.login.LoginActivity
import com.mobile.pft.reclamos.ui.activity.AddClaimActivity

@Composable
fun ExitButton() {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    IconButton(onClick = {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        activity?.finish()
    }) {
        Icon(
            imageVector = Icons.Filled.ExitToApp,
            contentDescription = "Logout"
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

@Composable
fun UtecIcon() {
    IconButton(onClick = { }) {
        Icon(
            painterResource(R.drawable.ic_utec_small_logo),
            contentDescription = "UTECLogo",
            tint = Color.Black,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun AddIcon(context: Context) {
    IconButton(onClick = { }) {
        IconButton(onClick = {
            context.startActivity(Intent(context, AddClaimActivity::class.java))
        }) {
            Icon(Icons.Filled.Add, contentDescription = "Pantalla de añadir un reclamo")
        }
    }
}