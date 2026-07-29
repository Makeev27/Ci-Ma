package com.makeev.cima.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.makeev.cima.R
import com.makeev.cima.presentation.screens.home.DrawRating
import com.makeev.cima.presentation.screens.model.MovieUiModel
import com.makeev.cima.ui.theme.CiMaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val query by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }

    // Автоматически вызываем клавиатуру при открытии экрана
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = viewModel::onQueryChange,
                        placeholder = { Text("Поиск...") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { viewModel.clearQuery(query) }) {
                                    Icon(Icons.Default.Close, contentDescription = "Очистить")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is SearchUiState.Idle -> {

                }
                is SearchUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is SearchUiState.Empty -> {
                    Text(
                        text = "Ничего не найдено",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SearchUiState.Error -> {
                    Text(
                        text = state.message,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is SearchUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 16.dp),
                    ) {
                        items(state.movies, key = { val id = it.id
                            id
                        }) { movie ->
                            VerticalMovie(
                                movie = movie,
                                onMovieClick = { onMovieClick(movie.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Dark Theme", showSystemUi = true)
@Composable
fun VerticalMoviePreview() {
    CiMaTheme(darkTheme = true) {
        Scaffold(modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
                innerPadding ->
            VerticalMovie(
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
fun VerticalMovie(
    modifier: Modifier = Modifier,
    movie: MovieUiModel,
    onMovieClick: (MovieUiModel) -> Unit
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onMovieClick(movie)
                },
                hapticFeedbackEnabled = true
            )
            .height(150.dp)
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


