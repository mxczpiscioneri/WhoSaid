package br.com.piscioneri.whosaid.data

data class Ranking(
    var quiz: Quiz? = null,
    var answers: List<RankingAnswer>? = null
)