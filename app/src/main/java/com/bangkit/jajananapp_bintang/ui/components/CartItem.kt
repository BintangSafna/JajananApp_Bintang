package com.bangkit.jajananapp_bintang.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.jajananapp_bintang.R
import com.bangkit.jajananapp_bintang.ui.theme.Shapes

@Composable
fun CartItem(
    rewardId: Int,
    image: String,
    title: String,
    totalPoint: Int,
    count: Int,
    onProductCountChanged: (id: Int, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, Shapes.small)
            .clip(Shapes.small)
            .padding(8.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(Shapes.small)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = stringResource(
                R.string.required_point,
                totalPoint
            ),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProductCounter(
            orderId = rewardId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(rewardId, count + 1) },
            onProductDecreased = { onProductCountChanged(rewardId, count - 1) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

