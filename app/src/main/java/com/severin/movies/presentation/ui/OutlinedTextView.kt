package com.severin.movies.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class OutlinedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val outlinedTitlePaint: Paint = Paint()

    override fun draw(canvas: Canvas) {

        val x = ZERO_COORDINATE
        val y = textSize
        val currentText = text.toString()

        outlinedTitlePaint.textAlign = Paint.Align.LEFT
        outlinedTitlePaint.strokeWidth = STROKE_WIDTH
        outlinedTitlePaint.textSize = textSize
        outlinedTitlePaint.style = Paint.Style.STROKE
        outlinedTitlePaint.strokeJoin = Paint.Join.ROUND
        outlinedTitlePaint.color = Color.WHITE
        canvas.drawText(currentText, x, y, outlinedTitlePaint)

        outlinedTitlePaint.style = Paint.Style.FILL
        outlinedTitlePaint.color = Color.BLACK
        canvas.drawText(currentText, x, y, outlinedTitlePaint)

        super.draw(canvas)
    }

    companion object {
        private const val ZERO_COORDINATE: Float = 0f
        private const val STROKE_WIDTH: Float = 15F
    }
}