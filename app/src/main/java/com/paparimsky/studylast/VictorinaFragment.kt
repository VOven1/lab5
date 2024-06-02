package com.paparimsky.studylast

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.paparimsky.studylast.databinding.FragmentVictorinaBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VictorinaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VictorinaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentVictorinaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var db: MainDB
    private val questionList = ArrayList<QuestionEntity>()
    private lateinit var iterator:MutableIterator<QuestionEntity>
    private var count = 0
    private var nextElement: QuestionEntity? = null
    private var countRightQuestion = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVictorinaBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.firstWidget.isVisible = true
        binding.secondWidget.isVisible = false
        binding.thirdWidget.isVisible = false
        db = MainDB.getDb(requireContext())
        countRightQuestion = 0
        count = 0

        db.getDao().getAllQuestion().asLiveData().observe(viewLifecycleOwner) { list ->
            if(list.isEmpty()) {
                val q1 = QuestionEntity(
                null,
                "Может ли лиса быть домашним животным?",
                "Нет",
                "Да, если ее чесать",
                "Да, если с ней гулять",
                "Да, если ее кормить",
                "Да, если ее кормить"
                )
            val q2 = QuestionEntity(
                null,
                "Чем кормить домашнюю лису?",
                "Мясными субпродуктами",
                "Кошачьим кормом",
                "Петрушкой",
                "Яблоками",
                "Мясными субпродуктами"
            )
            val q3 = QuestionEntity(
                null,
                "Подойдет ли для кормления лисы собачий корм?",
                "Нет",
                "Да, качественный",
                "Да, любой",
                "Да, Pedigree",
                "Да, качественный"
            )
            val q4 = QuestionEntity(
                null,
                "Нужно ли гулять с домашней лисой?",
                "Да, 3 часа в день",
                "Да, 1.5 часа в день",
                "Нет, на улице лисам холодно",
                "Нет, привыкшей к дому лисе не нужно гулять",
                "Да, 3 часа в день"
            )
            val q5 = QuestionEntity(
                null,
                "Нужен ли домашней лисе намордник для выгуливания?",
                "Да, она будет бегать за птицами и грызунами",
                "Нет, сытую лису не нужно мучать",
                "Нет, дорого",
                "Нет, никто не заметит",
                "Да, она будет бегать за птицами и грызунами"
            )
            val q6 = QuestionEntity(
                null,
                "Что делать если лиса съела что-то несъедобное?",
                "Отвезти к ветеринару",
                "Ничего",
                "Отвезти в лес",
                "Вытащить предмет самостоятельно",
                "Отвезти к ветеринару"
            )
            val q7 = QuestionEntity(
                null,
                "Как лечить больную лису?",
                "Не лечить",
                "Поливать святой водой",
                "Ветеринар выпишет таблетки или сделает уколы",
                "Дать ношпу",
                "Ветеринар выпишет таблетки или сделает уколы"
            )
            val q8 = QuestionEntity(
                null,
                "Можно ли вылечить лису самостоятельно?",
                "Да, главное надеть мед. перчатки",
                "Да, в любом случае",
                "Да, нурофеном",
                "Да, если есть лицензия ветеринара",
                "Да, если есть лицензия ветеринара"
            )
            val q9 = QuestionEntity(
                null,
                "Почему домашняя лиса не слушается?",
                "Нужно покупать больше игрушек",
                "Такая лиса",
                "Такой возраст у лисы",
                "Такой хозяин",
                "Такой хозяин"
            )
            val q10 = QuestionEntity(
                null,
                "За сколько можно продать шубу из лисы?",
                "2К",
                "5К",
                "10К",
                "15К",
                "15К"
            )
            Thread{
                db.getDao().insertQuestion(q1)
                db.getDao().insertQuestion(q2)
                db.getDao().insertQuestion(q3)
                db.getDao().insertQuestion(q4)
                db.getDao().insertQuestion(q5)
                db.getDao().insertQuestion(q6)
                db.getDao().insertQuestion(q7)
                db.getDao().insertQuestion(q8)
                db.getDao().insertQuestion(q9)
                db.getDao().insertQuestion(q10)
            }.start()
            }
        }



        binding.go.setTextColor(ContextCompat.getColor(requireContext(), R.color.lite_yellow))
        binding.go.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow))

        binding.startGame.setOnClickListener {
            binding.firstWidget.isVisible = false
            binding.secondWidget.isVisible = true
            db.getDao().getAllQuestion().asLiveData().observe(viewLifecycleOwner) { list ->
                questionList.clear()
                questionList.addAll(list)
                iterator = questionList.iterator()
                showQuestion()
            }
        }

        binding.firstBut.setOnClickListener {
            binding.choice1.isVisible = true
            if(nextElement?.first == nextElement?.right){
                countRightQuestion+=1
                binding.firstButCardView.strokeWidth = 4
                binding.firstButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.green)
            }else{
                wrongAnswer()
            }
            enableButtons()
        }
        binding.secondBut.setOnClickListener {
            binding.choice2.isVisible = true
            if(nextElement?.second == nextElement?.right){
                countRightQuestion+=1
                binding.secondButCardView.strokeWidth = 4
                binding.secondButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.green)
            }else{
                wrongAnswer()
            }
            enableButtons()
        }
        binding.thirdBut.setOnClickListener {
            binding.choice3.isVisible = true
            if(nextElement?.third == nextElement?.right){
                countRightQuestion+=1
                binding.thirdButCardView.strokeWidth = 4
                binding.thirdButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.green)
            }else{
                wrongAnswer()
            }
            enableButtons()
        }
        binding.fourthBut.setOnClickListener {
            binding.choice4.isVisible = true
            if(nextElement?.fourth == nextElement?.right){
                countRightQuestion+=1
                binding.fourthButCardView.strokeWidth = 4
                binding.fourthButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.green)
            }else{
                wrongAnswer()
            }
            enableButtons()
        }
        binding.go.setOnClickListener {
            showQuestion()
        }

        return view
    }
    private fun showQuestion(){
        if (iterator.hasNext()) {
            binding.choice1.isVisible = false
            binding.choice2.isVisible = false
            binding.choice3.isVisible = false
            binding.choice4.isVisible = false

            binding.wrong1.isVisible = false
            binding.wrong2.isVisible = false
            binding.wrong3.isVisible = false
            binding.wrong4.isVisible = false

            binding.firstButCardView.strokeWidth = 0
            binding.secondButCardView.strokeWidth = 0
            binding.thirdButCardView.strokeWidth = 0
            binding.fourthButCardView.strokeWidth = 0

            binding.firstBut.isEnabled = true
            binding.secondBut.isEnabled = true
            binding.thirdBut.isEnabled = true
            binding.fourthBut.isEnabled = true

            binding.go.isEnabled = false
            binding.go.setTextColor(ContextCompat.getColor(requireContext(), R.color.lite_yellow))
            binding.go.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow))

            count+=1
            nextElement = iterator.next()

            binding.number.text = "Вопрос ${count}/10"
            binding.question.text = nextElement!!.question
            binding.firstBut.text = nextElement!!.first
            binding.secondBut.text = nextElement!!.second
            binding.thirdBut.text = nextElement!!.third
            binding.fourthBut.text = nextElement!!.fourth
        }else{
            binding.secondWidget.isVisible = false
            binding.thirdWidget.isVisible = true
            binding.text2.text = "Вы ответили на ${countRightQuestion} вопросов из 10. Можно сказать, что вы хорошо знаете лисиц"
        }
    }

    private fun enableButtons(){
        binding.firstBut.isEnabled = false
        binding.secondBut.isEnabled = false
        binding.thirdBut.isEnabled = false
        binding.fourthBut.isEnabled = false
        binding.go.isEnabled = true
        binding.go.setTextColor(ContextCompat.getColor(requireContext(), R.color.background))
        binding.go.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.yellow))

    }

    private fun wrongAnswer(){
        if(nextElement?.second == nextElement?.right){
            binding.wrong2.isVisible = true
            binding.secondButCardView.strokeWidth = 2
            binding.secondButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.red)
        }else if(nextElement?.third == nextElement?.right){
            binding.wrong3.isVisible = true
            binding.thirdButCardView.strokeWidth = 2
            binding.thirdButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.red)
        }else if(nextElement?.fourth == nextElement?.right){
            binding.wrong4.isVisible = true
            binding.fourthButCardView.strokeWidth = 2
            binding.fourthButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.red)
        }else if(nextElement?.first == nextElement?.right){
            binding.wrong1.isVisible = true
            binding.firstButCardView.strokeWidth = 2
            binding.firstButCardView.strokeColor = ContextCompat.getColor(requireActivity(), R.color.red)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VictorinaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VictorinaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}