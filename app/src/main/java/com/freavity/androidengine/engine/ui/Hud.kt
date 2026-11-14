package com.freavity.androidengine.engine.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Hud {
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 36f
    }
    private val smallPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        textSize = 28f
    }

    fun drawText(canvas: Canvas, text: String, x: Float, y: Float) {
        canvas.drawText(text, x, y, textPaint)
    }

    fun drawSmall(canvas: Canvas, text: String, x: Float, y: Float) {
        canvas.drawText(text, x, y, smallPaint)
    }

    fun drawFps(canvas: Canvas, fps: Int) {
        canvas.drawText("FPS: $fps", 24f, 48f, smallPaint)
    }

    fun drawScore(canvas: Canvas, score: Int, width: Int) {
        val text = "Score: $score"
        val w = textPaint.measureText(text)
        canvas.drawText(text, width - w - 24f, 48f, textPaint)
    }
}
