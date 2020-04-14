package br.com.piscioneri.whosaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.piscioneri.whosaid.data.Quiz
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.GsonBuilder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

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
        val quiz = quizList[position]
        holder.name.text = quiz.name
        holder.count.text = "${quiz.phrases?.size} perguntas"
        Glide.with(holder.image)
            .load(quiz.image)
            .apply(
                RequestOptions.bitmapTransform(
                    RoundedCornersTransformation(
                        8,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
            )
            .into(holder.image)
        holder.card.setOnClickListener {
            val quizString = GsonBuilder().create().toJson(quiz)
            onClicked.invoke(quizString)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.item_card_view)
        val name: TextView = itemView.findViewById(R.id.item_name)
        val count: TextView = itemView.findViewById(R.id.item_count)
        val image: ImageView = itemView.findViewById(R.id.item_image)
    }
}