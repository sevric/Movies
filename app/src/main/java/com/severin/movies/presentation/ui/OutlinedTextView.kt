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

    override fun onDraw(canvas: Canvas) {

    }
    override fun draw(canvas: Canvas) {

        //Setting coordinates for the text extended by invisible dots
        val x = ZERO_COORDINATE
        val y = textSize
        val currentText = text.toString()

        //Setting actual width of the View as by the text extended by transparent dots
        val dummyTextExtendedByStartEndDots =
            String.format(DUMMY_TEXT_EXTENDED_BY_START_END_DOTS_TEMPLATE, currentText)
        width = outlinedTitlePaint.measureText(dummyTextExtendedByStartEndDots).toInt()

        //Installing general text presets
        outlinedTitlePaint.textAlign = Paint.Align.LEFT
        outlinedTitlePaint.textSize = textSize

        //Drawing transparent dot at the start edge
        outlinedTitlePaint.color = Color.TRANSPARENT
        outlinedTitlePaint.style = Paint.Style.FILL
        canvas.drawText(TRANSPARENT_DOT_TEXT_TO_DRAW, x, y, outlinedTitlePaint)

        //Drawing actual visible text stroke (outline)
        val xVisibleText = outlinedTitlePaint.measureText(TRANSPARENT_DOT_TEXT_TO_DRAW)
        outlinedTitlePaint.style = Paint.Style.STROKE
        outlinedTitlePaint.strokeWidth = STROKE_WIDTH
        outlinedTitlePaint.strokeJoin = Paint.Join.ROUND
        outlinedTitlePaint.color = Color.WHITE
        canvas.drawText(currentText, xVisibleText, y, outlinedTitlePaint)

        //Drawing actual text fill (the text itself)
        outlinedTitlePaint.style = Paint.Style.FILL
        outlinedTitlePaint.color = Color.BLACK
        canvas.drawText(currentText, xVisibleText, y, outlinedTitlePaint)

        //Setting new x coordinate for drawing the end dot
        val dummyTextExtendedByStartDot =
            String.format(DUMMY_TEXT_EXTENDED_BY_START_DOT_TEMPLATE, currentText)
        val xTransparentEndDot = outlinedTitlePaint.measureText(dummyTextExtendedByStartDot)
        //Drawing transparent dot at the end edge
        outlinedTitlePaint.color = Color.TRANSPARENT
        outlinedTitlePaint.style = Paint.Style.FILL
        canvas.drawText(TRANSPARENT_DOT_TEXT_TO_DRAW, xTransparentEndDot, y, outlinedTitlePaint)

        super.draw(canvas)
    }

    companion object {
        private const val ZERO_COORDINATE: Float = 0f
        private const val STROKE_WIDTH: Float = 10F
        private const val DUMMY_TEXT_EXTENDED_BY_START_END_DOTS_TEMPLATE = ".%1\$s."
        private const val DUMMY_TEXT_EXTENDED_BY_START_DOT_TEMPLATE = ".%1\$s"
        private const val TRANSPARENT_DOT_TEXT_TO_DRAW = "."
    }
}