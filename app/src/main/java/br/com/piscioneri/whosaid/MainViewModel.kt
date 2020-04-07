package br.com.piscioneri.whosaid

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.piscioneri.whosaid.data.Phrase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class MainViewModel : ViewModel() {

    private var _phrases: MutableLiveData<List<Phrase>> = MutableLiveData()

    init {
        getPhrases()
    }

    private fun getPhrases() {
        FirebaseFirestore.getInstance().collection("phrases")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("MXCZ", "Listen failed.", e)
                    _phrases.value = null
                    return@addSnapshotListener
                }

                val phraseList: MutableList<Phrase> = mutableListOf()
                for (doc in value!!) {
                    val phrase = doc.toObject<Phrase>()
                    phraseList.add(phrase)
                }
                _phrases.value = phraseList
                Log.d("MXCZ phraseList", phraseList.toString())
            }
    }

    internal var phrases: MutableLiveData<List<Phrase>> = _phrases
}
