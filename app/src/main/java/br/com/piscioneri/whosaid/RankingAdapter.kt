package br.com.piscioneri.whosaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.piscioneri.whosaid.data.Ranking
import com.google.gson.GsonBuilder

class RankingAdapter(
    private val rankingList: List<Ranking>,
    private val onClicked: (ranking: String) -> Unit
) :
    RecyclerView.Adapter<RankingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = rankingList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ranking = rankingList[position]
        holder.name.text = ranking.quiz!!.name
        holder.card.setOnClickListener {
            val rankingString = GsonBuilder().create().toJson(ranking)
            onClicked.invoke(rankingString)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.item_card_view)
        val name: TextView = itemView.findViewById(R.id.item_name)
    }
}