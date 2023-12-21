package com.dicoding.chickfarm.ui.screen.market

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.dicoding.chickfarm.R


@Composable
fun MarketScreen(
    modifier: Modifier = Modifier,
    navigateToPayment: (Int) -> Unit,
    context: Context,
    marketViewModel: MarketViewModel
) {
    val groupedProduct by marketViewModel.groupedProduct.collectAsState()
    val query by marketViewModel.query
    val searchValue by marketViewModel.searchValue

    Column(modifier = modifier) {
        SearchProductBar(
            query = query,
            onQueryChange = { newQuery ->
                marketViewModel.search(newQuery)
            },
            modifier = Modifier.padding(horizontal = 10.dp),
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxWidth()
        ) {


            groupedProduct.forEach { (init, data) ->

                items(data, key = { it.idProduk }) { data ->
                    ProductListItemGrid(
                        namaProduct = data.namaProduk, image = data.gambarProduk,
                        productId = data.idProduk, harga = data.hargaProduk,
                        navigateToPayment = navigateToPayment
                    )
                }
            }
//
        }

    }
    if (searchValue!= "") {
        marketViewModel.setQuery(searchValue)
        Toast.makeText(context, "Obat $searchValue", Toast.LENGTH_SHORT).show()
        marketViewModel.setSearhValue("")
    }
}


@Composable
fun ProductListItemGrid(
    modifier: Modifier = Modifier,
    namaProduct: String,
    image: String,
    productId: Int,
    harga: Int,
    navigateToPayment: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                navigateToPayment(productId)
            }
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model = image, contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(160.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(
                    text = namaProduct,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Rp. ${harga.toString()}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = modifier.padding(10.dp)
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
//    searchValue: String?
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
        },
        placeholder = {

            Text(
                text = stringResource(R.string.search_product),
                color = MaterialTheme.colorScheme.background
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            dividerColor = MaterialTheme.colorScheme.background,
            inputFieldColors = TextFieldDefaults.textFieldColors(
                MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.background
            )
        )
    ) {
    }
}


