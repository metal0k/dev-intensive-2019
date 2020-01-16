package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.log

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        log("onCreate")
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickname" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
            )
        btn_edit.setOnClickListener {
            isEditMode = !isEditMode
            setCurrentMode(isEditMode)
        }

    }

    private fun setCurrentMode(editMode: Boolean) {
        viewFields.filter { it.value is EditText }.values.forEach{ v ->
            v as EditText
            v.apply {
                isEnabled = editMode
                isFocusable = editMode
                isFocusableInTouchMode = editMode
                background.alpha = if(editMode) 255 else 0
            }
        }
        ic_eye.visibility = if(editMode) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = editMode
        with(btn_edit){
            val filter : ColorFilter? = if (editMode)
                PorterDuffColorFilter(getColor(R.color.color_accent), PorterDuff.Mode.SRC_IN)
            else null
            val icon = if(editMode) getDrawable(R.drawable.ic_save_black_24dp) else getDrawable(R.drawable.ic_edit_black_24dp)
            background.colorFilter = filter
            setImageDrawable(icon)

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        log("onRestoreInstanceState")
    }

}

