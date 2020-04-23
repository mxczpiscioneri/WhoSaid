package br.com.piscioneri.whosaid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.piscioneri.whosaid.data.Answer
import br.com.piscioneri.whosaid.data.Phrase
import br.com.piscioneri.whosaid.data.Quiz
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.GsonBuilder

class DetailViewModel : ViewModel() {

    var phrases: MutableLiveData<List<Phrase>> = MutableLiveData()
    var result: MutableLiveData<Int> = MutableLiveData(0)

    fun getQuiz(quizString: String): Quiz {
        return GsonBuilder().create().fromJson(quizString, Quiz::class.java)
    }

    fun getPhrases(quiz: Quiz) {
        Log.d("MXCZ getPhrases", quiz.phrases.toString())
        phrases.value = quiz.phrases
    }

    fun saveAnswer(answer: Answer) {
        if (answer.correct) {
            result.value = result.value?.plus(1)
        }
    }

    fun saveRanking(quizId: String, currentUser: String, name: String, value: Int) {
        Log.d("MXCZ saveRanking", "$quizId: $name: $value")

        val data = hashMapOf(
            "name" to name,
            "score" to value,
            "timestamp" to FieldValue.serverTimestamp()
        )
        val user = hashMapOf(
            currentUser to data
        )

        FirebaseFirestore.getInstance()
            .collection("ranking")
            .document(quizId)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("MXCZ", "ranking saved")
            }
            .addOnFailureListener { e ->
                Log.w("MXCZ", "Error adding document", e)
            }
    }
}
