package com.makeev.cima.domain.usecase

import com.makeev.cima.domain.model.Person
import com.makeev.cima.domain.repository.MovieRepository
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(personId: Int): Person {
        return repository.getPerson(personId)
    }

}