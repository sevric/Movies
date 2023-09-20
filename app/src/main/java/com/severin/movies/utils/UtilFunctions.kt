package com.severin.movies.utils

import android.content.Context
import androidx.room.util.newStringBuilder
import com.severin.movies.R
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.utils.ConstantSet.Companion.EMPTY_STRING_OF_GENRES
import com.severin.movies.utils.ConstantSet.Companion.GENRE_STRING_LIST_DELIMITER
import com.severin.movies.utils.ConstantSet.Companion.HYPHEN_SIGN_FOR_DATE_FORMAT
import com.severin.movies.utils.ConstantSet.Companion.NUMBER_OF_DIGITS_FOR_DATE_FORMAT
import com.severin.movies.utils.ConstantSet.Companion.STRING_DIGIT_FOR_DATE_FORMAT
import com.severin.movies.utils.ConstantSet.Companion.TIME_UNIT_INPUT_OFFSET
import com.severin.movies.utils.ConstantSet.Companion.UNITS_TO_PLUS_TO_COUNT_TIME_UNIT_FROM_ONE
import com.severin.movies.utils.ConstantSet.Companion.VALUE_OF_ROUNDING
import java.util.*
import kotlin.math.roundToInt

object UtilFunctions {

    fun getDateFormatted(dateToFormat: Calendar): String {
        val year = dateToFormat.get(Calendar.YEAR)
        val month = getFormattedTimeUnitString(dateToFormat.get(Calendar.MONTH), true)
        val date = getFormattedTimeUnitString(dateToFormat.get(Calendar.DATE), false)

        val strBuilder = newStringBuilder()

        return strBuilder
            .append(year)
            .append(HYPHEN_SIGN_FOR_DATE_FORMAT)
            .append(month)
            .append(HYPHEN_SIGN_FOR_DATE_FORMAT)
            .append(date)
            .toString()
    }

    //for api 21
    private fun getFormattedTimeUnitString(timeUnitToFormat: Int, isMonth: Boolean): String {
        val timeUnitCountedFromIndexOne = if (isMonth) {
            (timeUnitToFormat + UNITS_TO_PLUS_TO_COUNT_TIME_UNIT_FROM_ONE).toString()
        } else {
            timeUnitToFormat.toString()
        }
        val formattedTimeUnit = StringBuilder(timeUnitCountedFromIndexOne)

        //Check and format one decimal by adding 0 in front
        if (timeUnitCountedFromIndexOne.length < NUMBER_OF_DIGITS_FOR_DATE_FORMAT) {
            formattedTimeUnit.insert(TIME_UNIT_INPUT_OFFSET, STRING_DIGIT_FOR_DATE_FORMAT)
        }

        return formattedTimeUnit.toString()
    }

    fun getRoundedDouble(numberToRound: Double): Double {
        return (numberToRound * VALUE_OF_ROUNDING).roundToInt() / VALUE_OF_ROUNDING
    }

    fun getGenresString(movieItemApi: MovieItemApi): String {
        return if (movieItemApi.genres != null) {
            val genresList = movieItemApi.genres.map {
                it.name
            }
            genresList.joinToString(GENRE_STRING_LIST_DELIMITER).lowercase().replaceFirstChar {
                it.titlecase(
                    Locale.ROOT
                )
            }
        } else {
            EMPTY_STRING_OF_GENRES
        }
    }

    fun choosePeriod(period: String, context: Context): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()

        val monthOption = context.resources.getString(R.string.spinner_option_month)
        val yearOption = context.resources.getString(R.string.spinner_option_year)
        when (period) {
            monthOption -> calendar.add(Calendar.MONTH, -ConstantSet.ONE_UNIT_OF_TIME_FOR_SPINNER)
            yearOption -> calendar.add(Calendar.YEAR, -ConstantSet.ONE_UNIT_OF_TIME_FOR_SPINNER)
            else -> calendar.add(Calendar.WEEK_OF_YEAR, -ConstantSet.ONE_UNIT_OF_TIME_FOR_SPINNER)
        }

        return getDateFormatted(calendar)
    }
}