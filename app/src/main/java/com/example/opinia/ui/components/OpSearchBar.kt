package com.example.opinia.ui.components

import android.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// Projendeki font ailesi (eğer import hatası verirse burayı kontrol et)
import com.example.opinia.ui.theme.WorkSansFontFamily
import com.example.opinia.ui.theme.black

@Composable
fun OpSearchBar(
    value: String,                    // Diğer ekranlarla uyumlu kalsın diye 'value' bıraktım
    onValueChange: (String) -> Unit,  // 'onQueryChange' yerine 'onValueChange'
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp)), // İstediğin yuvarlaklık
        placeholder = {
            Text(
                "search",
                fontFamily = WorkSansFontFamily, // İstediğin font
                color = Color.Black // Arama yazısını siyah yapar
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, // buradaki virgüle dikkat
                contentDescription = "Search Icon", // buradaki virgüle dikkat
                tint = Color(0xFF0E4A6F)
            )
        },
        colors = TextFieldDefaults.colors(
            // İstediğin renk kodu: 0xFFD1E1F3
            focusedContainerColor = Color(0xFFD1E1F3),
            unfocusedContainerColor = Color(0xFFD1E1F3),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            // Yazı rengini siyah yapalım
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontFamily = WorkSansFontFamily)
    )
}

@Preview(showBackground = true)
@Composable
fun OpSearchBarPreview() {
    MaterialTheme {
        OpSearchBar(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}