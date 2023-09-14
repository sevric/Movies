package com.severin.movies.data.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.severin.movies.data.model.Genre

@TypeConverters
class MovieTypeConverter {
    @TypeConverter
    fun fromListOfIntToString(attribute: List<Int>?): String {
        if (attribute == null)
            return EMPTY_STRING
        return convertAnyToString(attribute)
    }

    @TypeConverter
    fun fromListOfGenresToString(attribute: List<Genre>?): String {
        if (attribute == null)
            return EMPTY_STRING
        return convertAnyToString(attribute)
    }

    @TypeConverter
    fun fromStringToListOfInt(attribute: String?): List<Int> {
        if (attribute == null)
            return emptyList()
        return convertStringToListOfInt(attribute)
    }

    @TypeConverter
    fun fromStringToListOfGenres(attribute: String?): List<Genre> {
        if (attribute == null)
            return emptyList()
        return convertStringToListOfGenres(attribute)
    }

    private fun convertAnyToString(attrs: List<Any>) : String {
        var convertedString = EMPTY_STRING

        for (att in attrs) {
            convertedString += " $att"
        }

        return convertedString.trim()
    }

    private fun convertStringToListOfInt(attribute: String?): List<Int> {
        if (attribute.isNullOrEmpty()) {
            return emptyList()
        }

        val listOfIntAsStrings = attribute.split(STRING_DELIMITER)
        val listOfInt = mutableListOf<Int>()

        listOfIntAsStrings.map {
            listOfInt.add(Integer.parseInt(it))
        }

        return listOfInt
    }

    private fun convertStringToListOfGenres(attribute: String?): List<Genre> {
        val listOfIntAsStrings = attribute?.split(STRING_DELIMITER)
        val listOfGenres = mutableListOf<Genre>()
        var index = ZERO_VALUE

        listOfIntAsStrings?.map {
            listOfGenres.add(Genre(index, it))
            index++
        }

        return listOfGenres
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val STRING_DELIMITER = " "
        private const val ZERO_VALUE = 0
    }
}