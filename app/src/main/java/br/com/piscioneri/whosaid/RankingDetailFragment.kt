package br.com.piscioneri.whosaid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.piscioneri.whosaid.data.Ranking
import kotlinx.android.synthetic.main.ranking_detail_fragment.*

class RankingDetailFragment : Fragment() {
    private val viewModel: RankingViewModel by viewModels()
    private lateinit var ranking: Ranking

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ranking_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ranking = requireArguments().getString("ranking")?.let { viewModel.getRankingDetail(it) }!!

        viewModel.getRankingAnswers(ranking)

        setupQuizDetail()

        subTitle.text = ranking.quiz?.name
        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupQuizDetail() {
        viewModel.answers.observe(viewLifecycleOwner, Observer {
            val layoutManager = GridLayoutManager(requireContext(), 1)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = RankingDetailAdapter(it)
        })
    }

}
