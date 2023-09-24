package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.severin.movies.data.model.MoviesResponse
import androidx.lifecycle.viewModelScope
import com.severin.movies.domain.GetNowInTheTheatersMoviesFromApiUseCase
import com.severin.movies.utils.UtilFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class NowInTheTheatersViewModel @Inject constructor(
private val getNowInTheTheatersMoviesFromApiUseCase: GetNowInTheTheatersMoviesFromApiUseCase
) : ViewModel() {
    val nowInTheTheatersLiveData = MutableLiveData<MoviesResponse>()

    fun getNowInTheTheatersMovies() = viewModelScope.launch(Dispatchers.IO) {
        val currentDateTwoMonthsBefore = Calendar.getInstance().also {
            it.add(Calendar.MONTH, -TWO_MONTHS)
        }
        val dateTwoMonthsAgo = UtilFunctions.getDateFormatted(currentDateTwoMonthsBefore)
        nowInTheTheatersLiveData.postValue(
            getNowInTheTheatersMoviesFromApiUseCase.invoke(dateTwoMonthsAgo)
        )
    }

    companion object {
        const val TWO_MONTHS = 2
    }
}