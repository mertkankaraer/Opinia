package com.example.opinia.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.opinia.ui.theme.NunitoFontFamily
import com.example.opinia.ui.theme.OpiniaPurple
import com.example.opinia.ui.theme.WorkSansFontFamily
import com.example.opinia.ui.theme.black

@Composable
fun DeleteCommentDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        containerColor = OpiniaPurple,
        title = {
            Text(
                text = "Are you sure you want to delete your comment?",
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = black
            )
        },
        text = {
            Text(
                text = "This action is irreversible. All data will be permanently deleted.",
                fontFamily = WorkSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = black
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.7f),
                    disabledContainerColor = Color.Red.copy(alpha = 0.3f),
                    contentColor = Color.White,
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                ),
            ) {
                Text(
                    text = "Delete Comment",
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Dismiss",
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = black
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteCommentDialogPreview() {
    DeleteCommentDialog(
        onConfirm = {},
        onDismiss = {}
    )
}