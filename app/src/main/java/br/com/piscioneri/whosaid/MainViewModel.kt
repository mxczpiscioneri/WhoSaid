package br.com.piscioneri.whosaid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val phrases: MutableLiveData<List<Phrases>> by lazy {
        MutableLiveData<List<Phrases>>().also {
            loadPhrases()
        }
    }

    fun getPhrases(): LiveData<List<Phrases>> {
        return phrases
    }

    private fun loadPhrases(): List<Phrases> {
        val phrases = ArrayList<Phrases>()
        phrases.add(
            Phrases(
                id = 1,
                name = "Yasaka Shrine",
                city = "Kyoto",
                url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"
            )
        )
        phrases.add(
            Phrases(
                id = 2,
                name = "Fushimi Inari Shrine",
                city = "Kyoto",
                url = "https://source.unsplash.com/NYyCqdBOKwc/600x800"
            )
        )
        phrases.add(
            Phrases(
                id = 3,
                name = "Bamboo Forest",
                city = "Kyoto",
                url = "https://source.unsplash.com/buF62ewDLcQ/600x800"
            )
        )
        phrases.add(
            Phrases(
                id = 4,
                name = "Brooklyn Bridge",
                city = "New York",
                url = "https://source.unsplash.com/THozNzxEP3g/600x800"
            )
        )
        phrases.add(
            Phrases(
                id = 5,
                name = "Empire State Building",
                city = "New York",
                url = "https://source.unsplash.com/USrZRcRS2Lw/600x800"
            )
        )
        return phrases
    }
}
