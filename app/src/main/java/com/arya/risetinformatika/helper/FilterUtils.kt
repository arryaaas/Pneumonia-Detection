package com.arya.risetinformatika.helper

import androidx.sqlite.db.SimpleSQLiteQuery

object FilterUtils {
    fun getFilteredQuery(filterType: FilterType): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM patient ")
        when (filterType) {
            FilterType.ALL -> {
                simpleQuery.append("ORDER BY id DESC")
            }
            FilterType.SAFE -> {
                simpleQuery.append("WHERE diagnostic_result LIKE '%safe%' ORDER BY id DESC")
            }
            FilterType.PNEUMONIA -> {
                simpleQuery.append("WHERE diagnostic_result LIKE '%pneumonia%' ORDER BY id DESC")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}