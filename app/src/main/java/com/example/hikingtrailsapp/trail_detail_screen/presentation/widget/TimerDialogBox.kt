package com.example.hikingtrailsapp.trail_detail_screen.presentation.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.hikingtrailsapp.core.viewmodel.SharedViewModel

@Composable
fun TimerDialogBox(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    val trailName = sharedViewModel.trailName.collectAsState(initial = "")

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = { 
            TextButton(
                onClick = { 
                    onConfirm()
                }
            ) {
                Text(text = "Tak")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Nie")
            }
        },
        title = {
            Text(
                text = "Czasomierz jest już aktywny!"
            )
        },
        text = {
            Text(
                text = "Czasomierz jest obecnie uruchomiony dla szlaku ${trailName.value}." +
                        "Czy chcesz uruchomić stoper dla obecnego szlaku i zatrzymać poprzedni?"
            )
        },
        icon = { Icons.Default.Info }
    )
}