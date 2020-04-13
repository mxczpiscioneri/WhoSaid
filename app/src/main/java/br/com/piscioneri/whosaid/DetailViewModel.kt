package br.com.piscioneri.whosaid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.piscioneri.whosaid.data.Answer
import br.com.piscioneri.whosaid.data.Phrase
import br.com.piscioneri.whosaid.data.Quiz
import com.google.gson.GsonBuilder

class DetailViewModel : ViewModel() {

    var phrases: MutableLiveData<List<Phrase>> = MutableLiveData()
    var result: MutableLiveData<Int> = MutableLiveData(0)

    fun getPhrases(quizString: String) {
        val quiz = GsonBuilder().create().fromJson(quizString, Quiz::class.java)
        Log.d("MXCZ getPhrases", quiz.phrases.toString())
        phrases.value = quiz.phrases
    }

    fun saveAnswer(answer: Answer) {
        if (answer.correct) {
            result.value = result.value?.plus(1)
        }
    }
}
