package com.makeev.cima.presentation.screens.person

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.makeev.cima.R
import com.makeev.cima.presentation.screens.detail.DetailScreenError
import com.makeev.cima.presentation.screens.home.ScreenLoading
import com.makeev.cima.presentation.screens.model.PersonDetailUiModel

@Composable
fun PersonScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    PersonScreenContent(
        uiState = uiState
    )
}

@Composable
fun PersonScreenContent(
    modifier: Modifier = Modifier,
    uiState: PersonScreenUiState,
) {

    when (uiState) {
        is PersonScreenUiState.Error -> {
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

        PersonScreenUiState.Loading -> {
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

        is PersonScreenUiState.Success -> {
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
                            PersonPoster(
                                person = uiState.person,
                                innerPadding = innerPadding
                            )
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = uiState.person.knownForDepartment,
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
                                    modifier = Modifier.fillMaxWidth(),
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
                                    text = "Биография",
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
                                ExpandableBiography(biography = uiState.person.biography)
                            }
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                                text = "Лучшие фильмы",
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
//                                items(items = uiState.cast, key = { it.id }) { cast ->
//                                    HorizontalMovieCast(cast = cast, onPersonClick = onPersonClick)
//                                }
                            }
                        }
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp),
                                text = "Лучшие сериалы",
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
//                                items(items = uiState.similarMovie, key = { it.id }) { movie ->
//                                    HorizontalSimilarMovie(
//                                        movie = movie,
//                                        onMovieClick = onSimilarMovieClick
//                                    )
//                                }
                            }
                        }
                    }

                }
            }

        }
    }

}

@Composable
fun PersonPoster(
    modifier: Modifier = Modifier,
    person: PersonDetailUiModel,
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
            model = person.profilePath,
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
                model = person.profilePath,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                fallback = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(innerPadding)
                    .width(180.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Text(
                text = person.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
        }

    }
}

@Composable
fun ExpandableBiography(
    modifier: Modifier = Modifier,
    biography: String,
    collapsedMaxLines: Int = 5
) {

    var isExpanded by remember { mutableStateOf(false) }
    var isOverFlowed by remember {mutableStateOf(false)}

    Column(modifier = modifier.animateContentSize()) {
        Text(
            text = biography,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            onTextLayout = {textLayoutResult ->
                if (!isOverFlowed) {
                    isOverFlowed = textLayoutResult.hasVisualOverflow
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        if (isOverFlowed || isExpanded) {
            Box(modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center) {
                Text(
                    if (isExpanded) "Скрыть" else "Читать полностью",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = {
                        isExpanded = !isExpanded
                    },
                        )
                )

            }
        }
    }

}

