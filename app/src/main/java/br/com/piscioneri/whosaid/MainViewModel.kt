package br.com.piscioneri.whosaid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.piscioneri.whosaid.data.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class MainViewModel : ViewModel() {

    var quiz: MutableLiveData<List<Quiz>> = MutableLiveData()

    init {
        getQuiz()
    }

    fun getQuiz() {
        FirebaseFirestore.getInstance().collection("quiz")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("MXCZ", "Listen failed.", e)
                    quiz.value = null
                    return@addSnapshotListener
                }

                val quizList: MutableList<Quiz> = mutableListOf()
                for (doc in value!!) {
                    val quiz = doc.toObject<Quiz>()
                    quiz.id = doc.id
                    quizList.add(quiz)
                }
                Log.d("MXCZ getQuiz", quizList.toString())
                quiz.value = quizList
            }
    }
}
