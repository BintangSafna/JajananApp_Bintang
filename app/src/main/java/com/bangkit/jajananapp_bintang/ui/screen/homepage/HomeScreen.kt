package com.bangkit.jajananapp_bintang.ui.screen.homepage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.jajananapp_bintang.JajananApp
import com.bangkit.jajananapp_bintang.R
import com.bangkit.jajananapp_bintang.data.model.OrderJajanan
import com.bangkit.jajananapp_bintang.data.model.dummyCategory
import com.bangkit.jajananapp_bintang.ui.common.UiState
import com.bangkit.jajananapp_bintang.ui.components.CategoryItem
import com.bangkit.jajananapp_bintang.ui.components.JajananListItem
import com.bangkit.jajananapp_bintang.ui.components.searchBar
import com.bangkit.jajananapp_bintang.ui.theme.FoodAppsTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    makananViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit,
) {
    makananViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                makananViewModel.getAllMakanan()
            }

            is UiState.Success -> {
                HomeContent(
                    orderMakanan = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    orderMakanan: List<OrderJajanan>,
    modifier: Modifier = Modifier,
    makananViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {

    val query by makananViewModel.query

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 60.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                stickyHeader {
                    searchBar(
                        query = query,
                        onQueryChange = makananViewModel::searchMakanan,
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                    )
                }

                if (orderMakanan.isEmpty()){
                    item {
                        Text(
                            text = "Data Tidak Ada!",
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }else{
                    items(orderMakanan) { data ->
                        JajananListItem(
                            nama = data.makanan.nama,
                            gambarUrl = data.makanan.gambarUrl,
                            harga = data.makanan.harga,
                            navigateToDetail = {
                                navigateToDetail(data.makanan.id)
                            }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Text(
                text = "Menu Pilihan Dari Beberapa Daerah",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(dummyCategory, key = { it.textCategory }) { category ->
                    CategoryItem(category)
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 70.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick = {
                scope.launch {
                    listState.scrollToItem(index = 0)
                }
            })
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    FoodAppsTheme {
        JajananApp()
    }
}
