package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R
import kotlin.math.min

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH_DP = 2f
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = dpToPix(DEFAULT_BORDER_WIDTH_DP)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPath = Path()

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getColor(
                R.styleable.CircleImageView_cv_borderColor,
                DEFAULT_BORDER_COLOR
            )
            borderWidth = a.getDimension(
                R.styleable.CircleImageView_cv_borderWidth,
                borderWidth
            )
            a.recycle()
        }
    }

    @Dimension
    fun getBorderWidth(): Int = pixToDp(borderWidth).toInt()

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = dpToPix(dp.toFloat())
        invalidate()
    }

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = resources.getColor(colorId, context.theme)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {

        val x = width / 2f
        val y = height / 2f
        val radius = min(x, y)

        clipPath.addCircle(x, y, radius, Path.Direction.CW)
        canvas?.clipPath(clipPath)

        super.onDraw(canvas)
        drawCircle(canvas, x, y, radius)
    }

    private fun drawCircle(canvas: Canvas?, x: Float, y: Float, radius: Float) {
        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = borderWidth

        canvas?.drawCircle(x, y, radius, paint)
    }

    private fun dpToPix(dp: Float): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )

    private fun pixToDp(px: Float): Float = px / Resources.getSystem().displayMetrics.density
}