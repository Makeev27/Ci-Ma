package com.makeev.cima.presentation.screens.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.makeev.cima.R
import com.makeev.cima.domain.MovieItem
import com.makeev.cima.ui.theme.CiMaTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onMovieClick: (MovieItem) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.loadMovies()
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.ShowToast -> Toast.makeText(
                    context, sideEffect.message, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    HomeScreenContent(modifier = modifier, state = uiState, onMovieClick = onMovieClick)
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: UiState = UiState.Success(generateFakeData()),
    onMovieClick: (MovieItem) -> Unit
) {

    var isBottomBarVisible by remember { mutableStateOf(true) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y < 0) isBottomBarVisible = false
                else if (available.y > 0) isBottomBarVisible = true
                return Offset.Zero
            }
        }
    }

    when (state) {
        is UiState.Error -> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    modifier = modifier,
                    topBar = {
                        TopAppBar()
                    },
                ) { innerPadding ->
                    HomeScreenError(innerPadding = innerPadding, onButtonClick = {})
                }
            }
        }

        UiState.Loading -> Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = modifier,
            ) { innerPadding ->
                HomeScreenLoading(innerPadding = innerPadding)
            }
        }

        is UiState.Success -> {
            Surface(
                modifier = modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    modifier = Modifier.nestedScroll(nestedScrollConnection),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = isBottomBarVisible,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            HomeScreenBottomBar() { }
                        }
                    }
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = innerPadding
                    ) {
                        item {
                            TopAppBar()
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                        item {
                            Subtitle(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "В тренде"
                            )
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                        item {
                            LazyRow(
                                modifier = Modifier,
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(items = state.items, key = { it.movieId }) { movieItem ->
                                    HorizontalMovieItem(movie = movieItem, onMovieItemClick = onMovieClick)
                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                        item {
                            Subtitle(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "Популярные фильмы"
                            )
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                        items(items = state.items, key = { it.movieId }) { movieItem ->
                            VerticalMovieItem(movie = movieItem, onMovieItemClick = onMovieClick)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Dark Theme", showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    CiMaTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreenContent(
                state = UiState.Success(generateFakeData()), onMovieClick = {}
            )
        }
    }
}

@Composable
fun HomeScreenLoading(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LinearProgressIndicator()
    }
}

@Composable
fun HomeScreenError(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
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
                        onButtonClick()
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "CineScope",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
        }

    }
}

@Composable
fun Subtitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun HorizontalMovieItem(
    modifier: Modifier = Modifier,
    movie: MovieItem,
    onMovieItemClick: (MovieItem) -> Unit
) {
    Card(
        modifier = modifier
            .width(120.dp)
            .aspectRatio(2 / 3f)

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = modifier.fillMaxSize(),
                model = movie.imageURL,
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.onSecondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${movie.year}",
                        color = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxHeight()
                    .combinedClickable(
                        onClick = {
                            onMovieItemClick(movie)
                        },
                        hapticFeedbackEnabled = true
                    ),
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
                        text = "${movie.rating}"
                    )
                }
            }
        }
    }
}

@Composable
fun VerticalMovieItem(
    modifier: Modifier = Modifier,
    movie: MovieItem,
    onMovieItemClick: (MovieItem) -> Unit
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onMovieItemClick(movie)
                },
                hapticFeedbackEnabled = true
            )
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(75.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(2f / 3f),
                model = movie.imageURL,
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
                        modifier = modifier.width(150.dp),
                        text = "The dark knight",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    DrawRating(rating = 8.0)
                }
                Text(
                    text = "${movie.year}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = movie.description,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenBottomBar(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NavigationBarItem(
            modifier = Modifier.padding(8.dp),
            onItemClick = {},
            icon = Icons.Outlined.Star,
            label = "Избранное"
        )
        NavigationBarItem(
            modifier = Modifier.padding(8.dp),
            onItemClick = {},
            icon = Icons.Outlined.Home,
            label = "Дом"
        )
        NavigationBarItem(
            modifier = Modifier.padding(8.dp),
            onItemClick = {},
            icon = Icons.Outlined.Person,
            label = "Профиль"
        )
    }

}


@Composable
fun NavigationBarItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    icon: ImageVector,
    label: String
) {
    Column(
        modifier = modifier
            .clickable(onClick = onItemClick),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Icon(
            imageVector = icon,
            contentDescription = ""
        )
        Text(
            text = label
        )
    }
}

@Composable
fun DrawRating(
    modifier: Modifier = Modifier,
    rating: Double
) {
    val counter = rating/2
    Row(modifier = modifier) {
        repeat(counter.toInt()) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Outlined.Star,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.tertiary,)
        }

    }
}

// FOR TESTS
fun generateFakeData(): List<MovieItem> {
    return mutableListOf<MovieItem>().apply {
        repeat(10) {
            add(
                MovieItem(
                    movieId = it,
                    title = "Title $it",
                    description = "Description $it",
                    rating = it.toDouble(),
                    year = it,
                    imageURL = "https://www.themoviedb.org/assets/2/v4/logos/v2/blue_square_2-d537fb228cf3ded904ef09b136fe3fec72548ebc1fea3fbbd1ad9e36364db38b.svg"
                )
            )
        }
    }
}
