package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import ru.skillbranch.devintensive.utils.Utils.makeTextBitmap
import java.util.*
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

private val colors = arrayOf( Color.parseColor("#d602ee"),
                              Color.parseColor("#6002ee"),
                              Color.parseColor("#ee6002"),
                              Color.parseColor("#9CCC65"),
                              Color.parseColor("#021aee"))

class AvatarImageView @JvmOverloads constructor(ctx: Context,
                                                attrs: AttributeSet? = null,
                                                defStyleAttr: Int = 0,
                                                defStyleRes: Int = 0
) : CircleImageView(ctx, attrs, defStyleAttr, defStyleRes) {

    private var initials: String = "??"
//    private val backgroundRandomColor = colors[abs(random.nextInt()) % colors.size]

    fun setInitials(newInitials: String) {
        initials = newInitials
        setInitialsBitmap(width, height)
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        setInitialsBitmap(w, h)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun setInitialsBitmap(w: Int, h: Int) {
        val colorFromInitials = initials.hashCode() % colors.size;
        if (w > 0 && h > 0) setImageBitmap(makeTextBitmap(initials, w, h, (min(w, h) * 0.4f),  Color.WHITE, colors[colorFromInitials]))
    }
}