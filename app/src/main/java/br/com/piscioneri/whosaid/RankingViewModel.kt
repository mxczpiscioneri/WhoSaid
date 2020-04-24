package br.com.piscioneri.whosaid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.piscioneri.whosaid.data.Ranking
import br.com.piscioneri.whosaid.data.RankingAnswer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.GsonBuilder

class RankingViewModel : ViewModel() {

    var ranking: MutableLiveData<List<Ranking>> = MutableLiveData()
    var answers: MutableLiveData<List<RankingAnswer>> = MutableLiveData()

    init {
        getRanking()
    }

    fun getRankingDetail(rankingString: String): Ranking? {
        return GsonBuilder().create().fromJson(rankingString, Ranking::class.java)
    }

    fun getRankingAnswers(ranking: Ranking) {
        Log.d("MXCZ getRankingAnswers", ranking.answers.toString())
        answers.value = ranking.answers
    }

    fun getRanking() {
        FirebaseFirestore.getInstance().collection("ranking")
            .orderBy("quiz.id")
            .get()
            .addOnSuccessListener { documents ->
                val rankingQuizList: MutableList<Ranking> = mutableListOf()
                for (doc in documents) {
                    val ranking = doc.toObject<Ranking>()

                    rankingQuizList.add(Ranking(ranking.quiz!!, ranking.answers!!))
                }
                Log.d("MXCZ getRanking", rankingQuizList.toString())
                ranking.value = rankingQuizList
                orderAnswers()
            }
            .addOnFailureListener { exception ->
                Log.e("MXCZ", "Listen failed.", exception)
                ranking.value = null
            }
    }

    private fun orderAnswers() {
        for (item in ranking.value!!) {
            item.answers = item.answers?.sortedWith(compareBy({ it.score }, { it.timestamp }))?.asReversed()
        }
    }
}
