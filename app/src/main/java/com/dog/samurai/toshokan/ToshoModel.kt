package com.dog.samurai.toshokan

data class ToshoModel(
        val systemid: String,
        val systemname: String,
        val libkey: String,
        val libid: Int,
        val short: String,
        val format: String,      // フルネーム
        val url_pc: String?,
        val address: String,
        val pref: String,
        val city: String,
        val post: String,
        val tel: String?,
        val geocode: String?,
        val category: String,
        val image: String
)

data class Callback(
        val callback: List<ToshoModel>
)

data class response(
        val response: Callback
)

/**
SMALL :	 図書室・公民館
MEDIUM :	 図書館(地域)
LARGE :	 図書館(広域)
UNIV :	 大学
SPECIAL :	 専門
BM :	 移動・BM
 **/
