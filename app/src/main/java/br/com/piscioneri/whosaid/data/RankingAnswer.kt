package br.com.piscioneri.whosaid.data

import com.google.firebase.Timestamp

data class RankingAnswer(
    var id: String? = null,
    val user: String? = "",
    val name: String = "",
    val score: Int = 0,
    val timestamp: Timestamp? = null
)