package com.example.opinia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.opinia.R

@Composable
fun RatingBar(rating: Int, maxRating: Int = 3, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(maxRating) { index ->
            val isSelected = index < rating
            Image(
                painter = painterResource(id = if (isSelected) R.drawable.kid_star_filled else R.drawable.kid_star),
                contentDescription = "$index Star",
                modifier = modifier.size(16.dp),
            )
        }
    }
}