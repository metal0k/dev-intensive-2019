package ru.skillbranch.devintensive.ui.profile

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.log
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.Utils
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

        et_repository.doAfterTextChanged {text ->
            wr_repository.error = text?.toString()?.let {
                if (it == "" || Utils.validateGithubURL(it)) "" else getString(R.string.msg_repository_error)
            } ?: ""
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(theme: Int?) {
        log("updateTheme from Observer")
        delegate.localNightMode = theme ?: AppCompatDelegate.MODE_NIGHT_NO

    }

    private fun updateUI(profile: Profile?) {
        log("updateUI from Observer")
        profile?.toMap().also {
            for ((k,v) in viewFields)
                v.text = it?.get(k)?.toString() ?: ""
        }
        tv_nick_name.text = profile?.nickName
        val initials = Utils.toInitials(profile?.firstName, profile?.lastName)
        initials?.also {
            val attrs = theme?.obtainStyledAttributes(IntArray(1, {R.attr.colorAccent}))
            val bgcolor = attrs?.getColor(0, 0) ?: 0
            attrs?.recycle()
            val bitmap =
                Utils.makeTextBitmap( initials,
                                      resources.getDimension(R.dimen.avatar_round_size).toInt(),
                                      resources.getDimension(R.dimen.avatar_round_size).toInt(),
                                      resources.getDimension(R.dimen.font_avatar_32),
                                      Color.WHITE,
                                      bgcolor
                                     )
            iv_avatar.setImageDrawable(BitmapDrawable(resources, bitmap))
        } ?: iv_avatar.setImageDrawable(resources.getDrawable(R.drawable.avatar_default, theme))

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
            repository = if(Utils.validateGithubURL(et_repository.text.toString())) et_repository.text.toString() else ""
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

