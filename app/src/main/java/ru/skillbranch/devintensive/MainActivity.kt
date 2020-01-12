package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.log_d
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {
    lateinit var bender: Bender;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bender = Bender()
        bender.restoreState(savedInstanceState)
        applyState()
//        tvQuestion.text = bender.askQuestion()
        iv_send.setOnClickListener(this)
        et_message.setOnEditorActionListener(this)
        log_d("onCreate")
    }

    fun applyState(text: String? = null) {
        log_d("applyState: $text")
        tv_text.text = text ?: bender.askQuestion()
        val (r,g,b) = bender.status.color
        iv_bender.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        log_d("right answers: ${bender.question.answers}")
    }

    override fun onRestart() {
        super.onRestart()
        log_d("onRestart")
    }

    fun processAnswer(answer: String) {
        log_d("processAnswer: $answer")
        var (question, color) = bender.listenAnswer(answer)
        applyState(question)
//        hideKeyboard()
    }

    override fun onClick(v: View?) {
        processAnswer(et_message.text.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bender.saveState(outState)
        log_d("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        log_d("onRestoreInstanceState")
    }

    override fun onPause() {
        super.onPause()
        log_d("onPause")
    }

    override fun onStart() {
        super.onStart()
        log_d("onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        log_d("onDestroy")
    }

    override fun onStop() {
        super.onStop()
        log_d("onStop")
    }

    override fun onResume() {
        super.onResume()
        log_d("onResume")
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE /*|| ( event?.action == KeyEvent.ACTION_UP && event?.keyCode == KeyEvent.KEYCODE_ENTER)*/) {
            log_d("onEditorAction: $actionId, $event")
            log_d("Enter or Done pressed")
            processAnswer(et_message.text.toString())
            hideKeyboard()
            return true
        }
        return false
    }
}

