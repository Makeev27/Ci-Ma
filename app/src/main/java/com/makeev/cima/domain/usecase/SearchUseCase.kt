package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.MultiSearchItem
import com.makeev.cima.domain.model.SearchCategory
import com.makeev.cima.domain.model.SearchCategory.ALL
import com.makeev.cima.domain.model.SearchCategory.MOVIES
import com.makeev.cima.domain.model.SearchCategory.PERSONS
import com.makeev.cima.domain.model.SearchCategory.TV_SHOWS
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(query: String, category: SearchCategory): List<MultiSearchItem> {
        return when (category) {
            ALL -> {
                repository.searchMulti(query)
            }

            MOVIES -> {
                repository.searchMovie(query)
            }

            TV_SHOWS -> {
                repository.searchTv(query)
            }

            PERSONS -> {
                repository.searchPerson(query)
            }
        }
    }

}