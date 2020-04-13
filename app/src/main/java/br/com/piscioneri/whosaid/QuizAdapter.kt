package br.com.piscioneri.whosaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.piscioneri.whosaid.data.Quiz
import com.google.gson.GsonBuilder

class QuizAdapter(
    private val quizList: List<Quiz>,
    private val onClicked: (quiz: String) -> Unit
) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = quizList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = quizList[position].name
        holder.name.setOnClickListener {
            val quizString = GsonBuilder().create().toJson(quizList[position])
            onClicked.invoke(quizString)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
    }
}