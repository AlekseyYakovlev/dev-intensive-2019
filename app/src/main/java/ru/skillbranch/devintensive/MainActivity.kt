package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity() {

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val statusName = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val questionName = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name

        benderObj = Bender(Bender.Status.valueOf(statusName), Bender.Question.valueOf(questionName))

        renderBendersState(benderObj.askQuestion(), benderObj.status.color)


        sendBtn.setOnClickListener {
            val (phase, color) = benderObj.listenAnswer(messageEt.text.toString())
            renderBendersState(phase, color)
        }

        messageEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val (phase, color) = benderObj.listenAnswer(messageEt.text.toString())
                renderBendersState(phase, color)
                true
            } else false
        }

    }

    private fun renderBendersState(question: String, color: Triple<Int, Int, Int>) {
        val (r, g, b) = color
        messageEt.setText("")
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = question
        hideKeyboard()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
    }
}
