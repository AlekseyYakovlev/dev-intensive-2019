package ru.skillbranch.devintensive.ui.custom

import android.graphics.*
import android.graphics.drawable.Drawable


class TextDrawable : Drawable() {
    var text: String = ""
    var backgroundColor = Color.BLACK
    var textColor = Color.WHITE

    var paint = Paint().apply {
        isAntiAlias = true
        isFakeBoldText = true
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        if (text.isBlank()) return
        canvas.drawRect(
            bounds,
            paint.apply { color = backgroundColor }
        )

        paint.textSize = bounds.width() * 0.5f

        val xPos = bounds.width() / 2f
        val yPos = (bounds.height() / 2 - (paint.descent() + paint.ascent()) / 2)
        //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.

        canvas.drawText(
            text,
            xPos, yPos,
            paint.apply { color = textColor }
        )
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}