package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.log_d
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var bender: Bender;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bender = Bender()
        bender.restoreState(savedInstanceState)
        applyState()
//        tvQuestion.text = bender.askQuestion()
        ivSend.setOnClickListener(this)
        log_d("onCreate")
    }

    fun applyState(text: String? = null) {
        log_d("applyState: $text")
        tvQuestion.text = text ?: bender.askQuestion()
        val (r,g,b) = bender.status.color
        ivBender.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        log_d("right answers: ${bender.question.answers}")
    }

    override fun onRestart() {
        super.onRestart()
        log_d("onRestart")
    }

    override fun onClick(v: View?) {
        var (question, color) = bender.listenAnswer(et_message.text.toString())
        applyState(question)

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
}

