package ru.skillbranch.devintensive.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint.Align
import android.graphics.RectF
import android.graphics.Typeface
import android.text.TextPaint


object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String?>? = fullName?.split(" ")?.map { if (it.isBlank()) null else it }
        return parts?.getOrNull(0) to parts?.getOrNull(1)
    }

    fun toInitials(firstName: String?, lastName: String?): String? =
        listOf(firstName, lastName)
            .filter { !it.isNullOrBlank() }
            .let {
                if (!it.isEmpty())
                    it.joinToString(separator = "")
                    { it?.get(0)?.toUpperCase().toString() }
                else
                    null
            }

    fun transliteration(payload: String, divider: String = " "): String {
        val transMap: Map<Char, String> = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )
        return payload
            .replace(Regex("\\s+"), divider)
            .toCharArray().fold("") { acc, chr ->
                if (transMap.containsKey(chr.toLowerCase()))
                    acc + (transMap.get(chr.toLowerCase()))?.let { if (chr.isUpperCase()) it.capitalize() else it }
                else
                    acc + chr


            }
    }

    fun validateGithubURL(url: String): Boolean {
        val invalidParts = listOf<String>("enterprise","features","topics",
                            "collections","trending","events",
                            "marketplace","pricing","nonprofit",
                            "customer-stories","security","login","join")
        val valRE = Regex("(?:https://)?(?:www\\.)?github\\.com/(?![\\W-])(?!\\w+-{2,})(?!(\\w*-\\w*)+-{2,})([a-zA-Z0-9-]+)(?<!-)/?")
//        val valRE = Regex("(?![\\W-])(?!\\w+-{2,})(?!(\\w*-\\w*)+-{2,})([a-zA-Z0-9-]+)(?<!-)")
        val matches = valRE.matchEntire(url)
        return  matches?.let{
            !invalidParts.contains(matches.groupValues[1].toLowerCase())
        }?: false
    }

    fun makeTextBitmap(text: String, width: Int, height: Int, textSize: Float, textColor: Int, backgroundColor: Int ): Bitmap {
        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)
        val textPaint = TextPaint()
        textPaint.apply {
            color = textColor
            typeface = Typeface.SANS_SERIF
            isAntiAlias = true
            isDither = true
            textAlign = Align.CENTER
            setTextSize(textSize)
        }
        val textHeight = textPaint.descent() - textPaint.ascent()
        val textOffset = textHeight / 2 - textPaint.descent()
        val bounds = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawText(text, bounds.centerX(), bounds.centerY() + textOffset, textPaint)
        return bitmap

    }
}





