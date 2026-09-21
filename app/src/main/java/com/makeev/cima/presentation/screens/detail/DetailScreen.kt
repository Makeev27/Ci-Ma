package com.makeev.cima.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.makeev.cima.R
import com.makeev.cima.presentation.screens.home.ScreenLoading
import com.makeev.cima.presentation.screens.model.MovieCastUiModel
import com.makeev.cima.presentation.screens.model.MovieDetailUiModel
import com.makeev.cima.ui.theme.CiMaTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel = hiltViewModel(),
    onSimilarMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    DetailScreenContent(
        modifier = modifier,
        uiState = uiState,
        onSimilarMovieClick = onSimilarMovieClick,
        onPersonClick = onPersonClick
    )

}

@Composable
fun DetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: DetailsUiState,
    onSimilarMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
) {

    when (uiState) {
        is DetailsUiState.Error -> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    modifier = modifier,
                ) { innerPadding ->
                    DetailScreenError(modifier = modifier.padding(innerPadding))
                }
            }
        }

        DetailsUiState.Loading -> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    modifier = modifier,
                ) { innerPadding ->
                    ScreenLoading(innerPadding = innerPadding)
                }
            }
        }

        is DetailsUiState.Success -> {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Scaffold() { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(
                            bottom = 24.dp
                        )
                    ) {
                        item {
                            DetailPoster(
                                movieDetails = uiState.movie,
                                innerPadding = innerPadding
                            )
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = uiState.movie.releaseDate,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = uiState.movie.runtime,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Box(contentAlignment = Alignment.Center) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Star,
                                            contentDescription = "Rating",
                                            tint = MaterialTheme.colorScheme.tertiary
                                        )
                                        Text(
                                            text = uiState.movie.voteAverage,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }

                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp
                                    ),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Button(
                                    modifier = Modifier
                                        .width(160.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onBackground,
                                        disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                        disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                                    ),
                                    onClick = {

                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Add to favorite"
                                    )
                                    Text(
                                        text = "В избранное"
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Button(
                                    modifier = Modifier
                                        .width(160.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onBackground,
                                        disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                                    ),
                                    onClick = {

                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Watch Trailer"
                                    )
                                    Text(
                                        text = "Трейлер"
                                    )
                                }
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "О фильме",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = uiState.movie.overview,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                                text = "Актеры",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentPadding = PaddingValues(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(items = uiState.cast, key = { it.id }) { cast ->
                                    HorizontalMovieCast(cast = cast, onPersonClick = onPersonClick)
                                }
                            }
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp),
                                text = "Похожие фильмы",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp)
                                    .height(150.dp),
                                contentPadding = PaddingValues(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(items = uiState.similarMovie, key = { it.id }) { movie ->
                                    HorizontalSimilarMovie(
                                        movie = movie,
                                        onMovieClick = onSimilarMovieClick
                                    )
                                }
                            }
                        }
                    }

                }
            }

        }
    }

}

@Preview(showBackground = true, name = "Dark Theme", showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    CiMaTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DetailScreenContent(
                uiState = DetailsUiState.Success(
                    MovieDetailUiModel(
                        "Shrek",
                        "2023",
                        "2H 15M",
                        "8.5",
                        "YEEEEAH",
                        "asdasdasdasdasdsa",
                        emptyList(),
                        budget = "1000000$",
                        posterPath = "/DDeITcCpnBd0CkAIRPhggy9bt5.jpg"
                    ),
                    listOf(
                        MovieCastUiModel(
                            "Shrek",
                            "John Adams",
                            9.9,
                            "/DDeITcCpnBd0CkAIRPhggy9bt5.jpg",
                            id = 0
                        )
                    ),
                    listOf(
                        MovieRecommendationsUiModel(
                            0,
                            "JohnWick",
                            "",
                            "10.0"
                        )
                    )
                ),
                onSimilarMovieClick = {},
                onPersonClick = {}
            )
        }
    }
}

@Composable
fun DetailScreenError(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        imageVector = Icons.Outlined.CloudOff,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Отсутствует подключение к серверу",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {

                        },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
                            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor
                        )
                    )
                    {
                        Text("Повторить попытку")
                    }
                }
            }
        }

    }
}

@Composable
fun DetailPoster(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetailUiModel,
    innerPadding: PaddingValues
) {
    Box(
        modifier = modifier
            .size(400.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = movieDetails.posterPath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 50.dp)
                .alpha(0.6f)
        )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = movieDetails.posterPath,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    fallback = painterResource(R.drawable.ic_launcher_background),
                    error = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clickable(
                            onClick = {

                            }
                        )
                        .padding(innerPadding)
                        .width(180.dp)
                        .height(250.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = movieDetails.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2
                )
            }

    }
}

@Composable
fun HorizontalMovieCast(
    modifier: Modifier = Modifier,
    cast: MovieCastUiModel,
    onPersonClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .clickable(
                    onClick = {
                        onPersonClick(cast.id)
                    }
                )
                .size(100.dp)
                .clip(CircleShape),

            ) {
            Box() {
                AsyncImage(
                    modifier = modifier.fillMaxSize(),
                    model = cast.profilePath,
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.Crop
                )
            }
        }
        Text(
            text = cast.name,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Composable
fun HorizontalSimilarMovie(
    modifier: Modifier = Modifier,
    movie: MovieRecommendationsUiModel,
    onMovieClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable(enabled = true, onClick = {
                onMovieClick(movie.id)
            })
            .width(120.dp)
            .aspectRatio(2 / 3f)

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = movie.posterPath,
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 8.dp, top = 8.dp)
                            .size(20.dp),
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Rating",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = movie.voteAverage
                    )
                }
            }
        }
    }
}

@Composable
fun YoutubeTrailerPlayer(
    modifier: Modifier = Modifier,
    detailUiModel: MovieDetailUiModel
) {
    AndroidView(modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        factory = {context ->
            YouTubePlayerView(context).apply {
                addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(detailUiModel.videos, 0.0f)
                    }
                })

            }
        })
}





