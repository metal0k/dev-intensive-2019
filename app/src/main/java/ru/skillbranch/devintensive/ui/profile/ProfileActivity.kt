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
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.log
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>
    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
        log("onCreate")
    }

    private fun initViews(savedInstanceState: Bundle?) {

        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
            )
        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        setCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileData()
            isEditMode = !isEditMode
            setCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(theme: Int?) {
        log("updateTheme")
        delegate.localNightMode = theme ?: AppCompatDelegate.MODE_NIGHT_NO

    }

    private fun updateUI(profile: Profile?) {
        log("updateUI from Observer")
        profile?.toMap().also {
            for ((k,v) in viewFields)
                v.text = it?.get(k)?.toString() ?: ""
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

    private fun saveProfileData() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).also {
            viewModel.saveProfileData(it)
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

