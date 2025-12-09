package com.example.opinia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.opinia.data.model.Avatar

@Composable
fun AvatarChooseBox(
    avatars: List<Avatar>,
    selectedAvatarId: String,
    onAvatarSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val orangeAvatar = avatars.find { it.key == "turuncu" }
    val purpleAvatar = avatars.find { it.key == "mor" }
    val greenAvatar = avatars.find { it.key == "turkuaz" }

    if (orangeAvatar != null && purpleAvatar != null && greenAvatar != null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                SelectableAvatarItems(
                    resId = orangeAvatar.resId,
                    isSelected = selectedAvatarId == orangeAvatar.key,
                    onClick = { onAvatarSelect(orangeAvatar.key) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    SelectableAvatarItems(
                        resId = purpleAvatar.resId,
                        isSelected = selectedAvatarId == purpleAvatar.key,
                        onClick = { onAvatarSelect(purpleAvatar.key) }
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    SelectableAvatarItems(
                        resId = greenAvatar.resId,
                        isSelected = selectedAvatarId == greenAvatar.key,
                        onClick = { onAvatarSelect(greenAvatar.key) }
                    )
                }
            }
        }
    }
}
