package br.com.piscioneri.whosaid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.piscioneri.whosaid.data.Answer
import br.com.piscioneri.whosaid.data.Phrase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class MainViewModel : ViewModel() {

    var phrases: MutableLiveData<List<Phrase>> = MutableLiveData()
    var result: MutableLiveData<Int> = MutableLiveData(0)

    init {
        getPhrases()
    }

    private fun getPhrases() {
        FirebaseFirestore.getInstance().collection("phrases")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("MXCZ", "Listen failed.", e)
                    phrases.value = null
                    return@addSnapshotListener
                }

                val phraseList: MutableList<Phrase> = mutableListOf()
                for (doc in value!!) {
                    val phrase = doc.toObject<Phrase>()
                    phrase.id = doc.id
                    phraseList.add(phrase)
                }
                phrases.value = phraseList
            }
    }

    fun saveAnswer(answer: Answer) {
        if (answer.right) {
            result.value = result.value?.plus(1)
        }
    }
}
