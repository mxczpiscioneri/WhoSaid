package br.com.piscioneri.whosaid.data

data class Quiz(
    var id: String = "",
    val name: String = "",
    val image: String? = "",
    val phrases: List<Phrase>? = null
)