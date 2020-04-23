package br.com.piscioneri.whosaid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.com.piscioneri.whosaid.data.Answer
import br.com.piscioneri.whosaid.data.Phrase
import br.com.piscioneri.whosaid.data.Quiz
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*

class DetailFragment() : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var currentUser: String = UUID.randomUUID().toString()
    private val viewModel: DetailViewModel by viewModels()
    private var listSize: Int = 0
    private lateinit var quiz: Quiz
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

        quiz = requireArguments().getString("quiz")?.let { viewModel.getQuiz(it) }!!

        viewModel.getPhrases(quiz)
        setupCardStack()
        login()
    }

    private fun login() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user == null) {
            auth.signInAnonymously()
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        currentUser = auth.currentUser!!.uid
                    }
                }
        } else {
            currentUser = user.uid
        }
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
        dialog.titleText = getString(R.string.right)
        dialog.contentText =
            "${phrase.source!!.description}<br/><br/>${phrase.source.name}"
        dialog.setCancelable(false)
        dialog.confirmText = getString(R.string.next)
        dialog.setConfirmClickListener {
            swipe()
            dialog.dismissWithAnimation()
        }
        dialog.show()
    }

    private fun alertError(phrase: Phrase) {
        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
        dialog.titleText = getString(R.string.wrong)
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
        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
        dialog.titleText = getString(R.string.result)
        dialog.contentText = "Você acertou $value das $listSize frases."
        dialog.setCancelable(false)
        dialog.cancelText = getString(R.string.initial_screen)
        dialog.setCancelClickListener {
            dialog.dismissWithAnimation()
            backInitialScreen()
        }
        dialog.confirmText = getString(R.string.save)
        dialog.setConfirmClickListener {
            val editText = EditText(context)
            editText.setHint(R.string.input_name)

            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(editText)

            it.titleText = getString(R.string.save_in_ranking)
            it.setCustomView(linearLayout)
            it.setCancelable(false)
            it.cancelText = getString(R.string.initial_screen)
            it.setCancelClickListener { it2 ->
                it2.dismissWithAnimation()
                backInitialScreen()
            }
            it.setConfirmClickListener { it2 ->
                viewModel.saveRanking(quiz.id, currentUser!!, editText.text.toString(), value)
                it2.dismissWithAnimation()
                backInitialScreen()
            }
            it.changeAlertType(SweetAlertDialog.NORMAL_TYPE);
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
