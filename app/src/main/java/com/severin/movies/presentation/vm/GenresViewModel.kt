package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.GenresResponse
import com.severin.movies.domain.GetGenresListFromApiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel constructor(
    private val getGenresListFromApiUseCase: GetGenresListFromApiUseCase
) : ViewModel() {
    val genresFromApi = MutableLiveData<GenresResponse>()

    fun getGenres() = viewModelScope.launch(Dispatchers.IO) {
        genresFromApi.postValue(
            getGenresListFromApiUseCase.invoke()
        )
    }
}