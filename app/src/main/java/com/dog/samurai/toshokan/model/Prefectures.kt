package com.dog.samurai.toshokan.model

data class Prefectures(
        val prefCode: Int,
        val prefName: String
)

data class Result(
        val result: List<Prefectures>
)