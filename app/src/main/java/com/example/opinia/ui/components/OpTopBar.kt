package com.example.opinia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.opinia.R
import com.example.opinia.ui.theme.NunitoFontFamily
import com.example.opinia.ui.theme.black
import com.example.opinia.ui.theme.white

@Composable
fun OpTopBar(
    title: String,
    avatarResId: Int,
    onAvatarClick: () -> Unit
) {
    // DashboardHeader ile birebir aynı yapı
    Row(
        modifier = Modifier.fillMaxWidth(),
        // Paddingleri kaldırdık, çünkü parent Column zaten 24dp veriyor.
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // SOL TARAF (LOGO + BAŞLIK)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.lacivert_amblem),
                contentDescription = "App Logo",
                modifier = Modifier.size(50.dp) // Dashboard boyutu
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                color = black,
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    fontFamily = NunitoFontFamily
                )
            )
        }

        // SAĞ TARAF (AVATAR)
        Image(
            painter = painterResource(id = avatarResId),
            contentDescription = "Profile Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp) // Dashboard boyutu
                .clip(CircleShape)
                .clickable { onAvatarClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OpTopBarPreview() {
    MaterialTheme {
        OpTopBar(
            title = "Professors",
            avatarResId = R.drawable.ic_launcher_foreground,
            onAvatarClick = {}
        )
    }
}