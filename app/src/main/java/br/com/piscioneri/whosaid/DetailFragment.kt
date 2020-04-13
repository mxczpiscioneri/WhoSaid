package br.com.piscioneri.whosaid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.com.piscioneri.whosaid.data.Answer
import br.com.piscioneri.whosaid.data.Phrase
import cn.pedant.SweetAlert.SweetAlertDialog
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

class DetailFragment() : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var listSize: Int = 0
    private lateinit var cardStackView: CardStackView
    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).setSupportActionBar(app_bar)

        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val quiz = requireArguments().getString("quiz")

        viewModel.getPhrases(quiz!!)
        setupCardStack()
    }

    private fun setupCardStack() {
        cardStackLayoutManager = CardStackLayoutManager(requireContext())
        cardStackLayoutManager.setMaxDegree(0f)
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Automatic)

        cardStackView = card_stack_view
        cardStackView.layoutManager = cardStackLayoutManager

        val onClicked: (phrase: Phrase, answer: Answer) -> Unit = { phrase, answer ->
            viewModel.saveAnswer(answer)

            if (answer.correct) {
                alertSuccess(phrase)
            } else {
                alertError(phrase)
            }
        }

        viewModel.phrases.observe(viewLifecycleOwner, Observer {
            listSize = it.size
            cardStackView.adapter = CardStackAdapter(it, onClicked)
        })
    }

    private fun alertSuccess(phrase: Phrase) {
        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
        dialog.titleText = resources.getString(R.string.right)
        dialog.contentText =
            "${phrase.source!!.description}<br/><br/>${phrase.source.name}"
        dialog.setCancelable(false)
        dialog.confirmText = resources.getString(R.string.next)
        dialog.setConfirmClickListener {
            swipe()
            dialog.dismissWithAnimation()
        }
        dialog.show()
    }

    private fun alertError(phrase: Phrase) {
        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
        dialog.titleText = resources.getString(R.string.wrong)
        dialog.contentText =
            "${phrase.source!!.description}<br/><br/>${phrase.source.name}"
        dialog.setCancelable(false)
        dialog.confirmText = resources.getString(R.string.next)
        dialog.setConfirmClickListener {
            swipe()
            dialog.dismissWithAnimation()
        }
        dialog.show()
    }

    private fun alertResult(value: Int) {
        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.NORMAL_TYPE)
        dialog.titleText = resources.getString(R.string.result)
        dialog.contentText = "Você acertou $value das $listSize frases."
        dialog.setCancelable(false)
        dialog.confirmText = resources.getString(R.string.initial_screen)
        dialog.setConfirmClickListener {
            dialog.dismissWithAnimation()
            backInitialScreen()
        }
        dialog.show()
    }

    private fun swipe() {
        if (cardStackView.size == 1) {
            alertResult(viewModel.result.value ?: 0)
        } else {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Slow.duration)
                .build()

            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }

    private fun backInitialScreen() {
        val fm: FragmentManager? = activity?.supportFragmentManager
        if (fm?.backStackEntryCount!! > 0) {
            fm.popBackStack()
        } else {
            activity?.onBackPressed()
        }
    }
}
