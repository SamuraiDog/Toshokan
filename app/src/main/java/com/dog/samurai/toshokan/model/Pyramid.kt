package com.dog.samurai.toshokan.model

import com.google.gson.annotations.SerializedName

data class Pyramid(
        val result: PyramidResult
)

data class PyramidResult(
        val yearLeft: PyramidYearData,
        val yearRight: PyramidYearData
)

data class PyramidYearData(
        val year: Int,
        val oldAgeCount: Int,
        val oldAgePercent: Float,
        val middleAgeCount: Int,
        val middleAgePercent: Float,
        val newAgeCount: Int,
        val newAgePercent: Float,
        val data: List<PyramidDataDesc>
)

data class PyramidDataDesc(
        @SerializedName("class")
        val ageClass: String,
        val man: Int,
        val manPercent: Float,
        val woman: Int,
        val womanPercent: Float
)