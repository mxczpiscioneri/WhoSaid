package br.com.piscioneri.whosaid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.quiz_fragment.*

class QuizFragment : Fragment() {

    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.quiz_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupQuiz()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getQuiz()
        }

        btn_info.setOnClickListener {
            Toast.makeText(context, R.string.development, Toast.LENGTH_LONG).show()
        }
        btn_ranking.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.add(
                R.id.container,
                RankingFragment()
            )?.addToBackStack(null)?.commit()
        }
    }

    private fun setupQuiz() {
        val onClicked: (quiz: String) -> Unit = { quiz ->
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString("quiz", quiz)
            fragment.arguments = args

            activity?.supportFragmentManager?.beginTransaction()?.add(
                R.id.container,
                fragment
            )?.addToBackStack(null)?.commit()
        }

        viewModel.quiz.observe(viewLifecycleOwner, Observer {
            val layoutManager = GridLayoutManager(requireContext(), 1)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = QuizAdapter(it, onClicked)
            swipeRefreshLayout.isRefreshing = false
        })
    }
}
