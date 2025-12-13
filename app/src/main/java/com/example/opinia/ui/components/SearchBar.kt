package com.example.opinia.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.opinia.ui.theme.OpiniaDeepBlue
import com.example.opinia.ui.theme.OpinialightBlue
import com.example.opinia.ui.theme.black

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .width(270.dp)
            .padding(start = 16.dp, end = 16.dp),
        shape = MaterialTheme.shapes.extraLarge,
        placeholder = {
            Text("Search", style = MaterialTheme.typography.bodyMedium, color = black)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = OpiniaDeepBlue)
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = black,
            unfocusedTextColor = black,
            focusedContainerColor = OpinialightBlue,
            unfocusedContainerColor = OpinialightBlue,
            cursorColor = OpiniaDeepBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}