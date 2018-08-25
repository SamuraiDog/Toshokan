package com.dog.samurai.toshokan

data class PrefecturesModel(
        val prefCode: Int,
        val prefName: String
)

data class Result(
        val result: List<PrefecturesModel>
)