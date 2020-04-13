package br.com.piscioneri.whosaid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).setSupportActionBar(app_bar)

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupQuiz()
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
            val layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = QuizAdapter(it, onClicked)
        })
    }
}
