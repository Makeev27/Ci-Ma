package com.makeev.cima.utils

import com.makeev.cima.domain.model.PopularMovie
import com.makeev.cima.domain.model.TrendingMovie

fun getReleaseYear(popularMovie: PopularMovie): String {
        return popularMovie.releaseDate.split("-").first()
    }
fun getReleaseYear(trendingMovie: TrendingMovie): String {
        return trendingMovie.releaseDate.split("-").first()
    }
