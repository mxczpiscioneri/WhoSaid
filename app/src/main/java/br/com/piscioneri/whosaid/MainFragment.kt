package br.com.piscioneri.whosaid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.com.piscioneri.whosaid.data.Answer
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var cardStackView: CardStackView
    private lateinit var cardStackLayoutManager: CardStackLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupCardStack()
    }

    private fun setupCardStack() {
        cardStackLayoutManager = CardStackLayoutManager(requireContext())
        cardStackLayoutManager.setStackFrom(StackFrom.Right)
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Automatic)

        cardStackView = card_stack_view
        cardStackView.layoutManager = cardStackLayoutManager

        val onClicked: (answer: Answer) -> Unit = { answer ->
            viewModel.saveAnswer(answer)

            if (cardStackView.size == 1) {
                Log.d("MXCZ", "resultado: " + viewModel.result.value)
            } else {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .build()

                cardStackLayoutManager.setSwipeAnimationSetting(setting)
                cardStackView.swipe()
            }
        }

        viewModel.phrases.observe(viewLifecycleOwner, Observer {
            cardStackView.adapter = CardStackAdapter(it, onClicked)
        })
    }
}
