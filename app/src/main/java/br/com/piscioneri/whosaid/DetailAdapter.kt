package br.com.piscioneri.whosaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.piscioneri.whosaid.data.Answer
import br.com.piscioneri.whosaid.data.Phrase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class DetailAdapter(
    private val phrases: List<Phrase> = emptyList(),
    private val onClicked: (phrase: Phrase, answer: Answer) -> Unit
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_detail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phrase = phrases[position]
        holder.text.text = "\"${phrase.text}\""
        holder.sourceName.text = phrase.source?.name
        holder.sourceDescription.text = phrase.source?.description
        Glide.with(holder.image)
            .load(phrase.image)
            .apply(
                bitmapTransform(
                    RoundedCornersTransformation(
                        32,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
            )
            .into(holder.image)
        holder.answer1.text = phrase.answer?.get(0)?.text
        holder.answer1.setOnClickListener {
            onClicked.invoke(phrase, phrase.answer?.get(0)!!)
        }
        holder.answer2.text = phrase.answer?.get(1)?.text
        holder.answer2.setOnClickListener {
            onClicked.invoke(phrase, phrase.answer?.get(1)!!)
        }
    }

    override fun getItemCount(): Int {
        return phrases.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.item_text)
        val sourceName: TextView = view.findViewById(R.id.item_source_name)
        val sourceDescription: TextView = view.findViewById(R.id.item_source_description)
        val image: ImageView = view.findViewById(R.id.item_image)
        val answer1: Button = view.findViewById(R.id.btn_answer1)
        val answer2: Button = view.findViewById(R.id.btn_answer2)
    }

}