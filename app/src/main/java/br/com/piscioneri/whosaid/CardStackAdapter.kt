package br.com.piscioneri.whosaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.piscioneri.whosaid.data.Phrase
import com.bumptech.glide.Glide

class CardStackAdapter(
    private var phrases: List<Phrase> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_spot,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phrase = phrases[position]
        holder.name.text = phrase.text
//        holder.city.text = phrase.city
        Glide.with(holder.image)
            .load(phrase.image)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, phrase.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return phrases.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}