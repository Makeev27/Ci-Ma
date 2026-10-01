package com.makeev.cima.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.makeev.cima.R
import com.makeev.cima.domain.model.SearchCategory
import com.makeev.cima.presentation.screens.home.DrawRating
import com.makeev.cima.presentation.screens.model.MovieUiModel
import com.makeev.cima.presentation.screens.model.MultiSearchUiItem
import com.makeev.cima.presentation.screens.model.PersonUiModel
import com.makeev.cima.presentation.screens.model.TvShowUiModel
import com.makeev.cima.ui.theme.CiMaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()
    val scope = rememberCoroutineScope()

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textFieldState,
            searchBarState = searchBarState,
            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
            placeholder = { Text("Поиск...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Поиск") },
            trailingIcon = {
                if (textFieldState.text.isNotEmpty()) {
                    IconButton(onClick = {
                        textFieldState.edit { replace(0, length, "") }
                    }) {
                        Icon(Icons.Default.Close, "Очистить")
                    }
                }
            }
        )
    }

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text }
            .collect { text ->
                viewModel.onQueryChange(text.toString())
            }
    }

    SearchScreenContent(
        onItemClick = onItemClick,
        uiState = uiState,
        searchBarState = searchBarState,
        scope = scope,
        inputField = inputField,
        onButtonClick = {},
        selectedCategory = selectedCategory,
        onCategorySelected = viewModel::onCategorySelected
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    searchBarState: SearchBarState,
    onButtonClick: () -> Unit,
    selectedCategory: SearchCategory,
    onCategorySelected: (SearchCategory) -> Unit,
    scope: CoroutineScope,
    inputField: @Composable () -> Unit,
    uiState: SearchUiState,
) {
    Scaffold(
        topBar =
            {
                Column{
                    TopSearchBar(
                        state = searchBarState,
                        inputField = inputField,
                        modifier = Modifier.fillMaxWidth()
                    )
                    SearchCategoryRow(
                        selectedCategory = selectedCategory,
                        onCategorySelected = onCategorySelected
                    )
                }
            }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                SearchUiState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ничего не найдено...",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                is SearchUiState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }

                SearchUiState.Idle -> {
                }

                SearchUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is SearchUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(items = uiState.items, key = {
                            it
                        }) { item ->
                            VerticalItem(item = item, onItemClick = onItemClick, uiState = uiState)
                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Dark Theme", showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    CiMaTheme(darkTheme = true) {
        SearchScreenContent(
            onItemClick = {}, uiState = SearchUiState.Idle,
            searchBarState = rememberSearchBarState(),
            scope = rememberCoroutineScope(), inputField = @Composable {}, onButtonClick = {},
            selectedCategory = SearchCategory.ALL, onCategorySelected = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme", showSystemUi = true)
@Composable
fun VerticalMoviePreview() {
    CiMaTheme(darkTheme = true) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) { innerPadding ->
            SearchMovieItem(
                modifier = Modifier.padding(innerPadding),
                movie = MovieUiModel(
                    id = 0, overview = "Overview", posterPath = "",
                    releaseDate = "25.07.2026",
                    title = "Shrek",
                    voteAverage = "10.0",
                    voteCount = 23,
                    popularity = 23.0
                ),
                onMovieClick = {}
            )

        }
    }
}

@Composable
fun VerticalItem(
    modifier: Modifier = Modifier,
    item: MultiSearchUiItem,
    uiState: SearchUiState.Success,
    onItemClick: (Int) -> Unit
) {
    when (uiState.category) {
        SearchCategory.ALL -> {

        }
        SearchCategory.MOVIES -> {
            SearchMovieItem(movie = (item as MovieUiModel), onMovieClick = onItemClick)
        }
        SearchCategory.TV_SHOWS -> {
            SearchTvShowItem(tvShow = (item as TvShowUiModel), onTvShowClick = onItemClick)
        }
        SearchCategory.PERSONS -> {
            SearchPersonItem(person = (item as PersonUiModel), onPersonClick = onItemClick)
        }
    }
}

@Composable
fun SearchMovieItem(
    modifier: Modifier = Modifier,
    movie: MovieUiModel,
    onMovieClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable(
                onClick = {
                    onMovieClick(movie.id)
                }
            )
            .heightIn(min = 150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(75.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(2f / 3f),
                model = movie.posterPath,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                fallback = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.width(150.dp),
                        text = movie.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    DrawRating(voteAverage = movie.voteAverage)
                }
                Text(
                    text = movie.releaseDate,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = movie.overview,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SearchTvShowItem(
    modifier: Modifier = Modifier,
    tvShow: TvShowUiModel,
    onTvShowClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable(
                onClick = {
                    onTvShowClick(tvShow.id)
                }
            )
            .heightIn(min = 150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(75.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(2f / 3f),
                model = tvShow.posterPath,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                fallback = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.width(150.dp),
                        text = tvShow.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    DrawRating(voteAverage = tvShow.voteAverage)
                }
                Text(
                    text = "${tvShow.firstAirDate} - ${tvShow.lastAirDate}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = tvShow.overview,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun SearchPersonItem(
    modifier: Modifier = Modifier,
    person: PersonUiModel,
    onPersonClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable(
                onClick = {
                    onPersonClick(person.id)
                }
            )
            .heightIn(min = 150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(75.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(2f / 3f),
                model = person.profilePath,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                fallback = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.width(150.dp),
                        text = person.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = person.birthday,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = person.knownForDepartment,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun SearchCategoryRow(
    modifier: Modifier = Modifier,
    selectedCategory: SearchCategory,
    onCategorySelected: (SearchCategory) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(SearchCategory.entries) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.title) }
            )
        }
    }
}