package com.dog.samurai.toshokan.model

data class VisitorFrom(
        val prefName: String,
        val changes: List<Changes>
)

data class Changes(
        val countryName: String,
        val data: List<Data>
)

data class Data(
        val year: Int,
        val quarter: Int,
        val value: Int
)

data class VisitorResult(
        val result: VisitorFrom
)