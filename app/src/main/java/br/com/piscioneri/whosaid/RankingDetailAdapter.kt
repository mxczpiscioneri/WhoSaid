package br.com.piscioneri.whosaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.piscioneri.whosaid.data.RankingAnswer

class RankingDetailAdapter(
    private val rankingList: List<RankingAnswer>
) :
    RecyclerView.Adapter<RankingDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ranking_detail, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = rankingList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ranking = rankingList[position]
        holder.name.text = "${ranking.name}: ${ranking.score}"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item_name)
    }
}