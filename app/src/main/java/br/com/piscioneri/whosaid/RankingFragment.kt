package br.com.piscioneri.whosaid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.ranking_fragment.*

class RankingFragment : Fragment() {

    private val viewModel: RankingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.ranking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRanking()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getRanking()
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRanking() {
        val onClicked: (ranking: String) -> Unit = { ranking ->
            val fragment = RankingDetailFragment()
            val args = Bundle()
            args.putString("ranking", ranking)
            fragment.arguments = args

            activity?.supportFragmentManager?.beginTransaction()?.add(
                R.id.container,
                fragment
            )?.addToBackStack(null)?.commit()
        }

        viewModel.ranking.observe(viewLifecycleOwner, Observer {
            val layoutManager = GridLayoutManager(requireContext(), 1)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = RankingAdapter(it, onClicked)
            swipeRefreshLayout.isRefreshing = false
        })
    }
}
