package com.example.gaenari

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircularProgressButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val progressPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 16f
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.mainyellow)
    }

    @SuppressLint("ResourceAsColor")
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = R.color.dd5
    }

    private var progress = 0f // 진행률

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f
        val radius = (Math.min(width, height) / 2f) - 20f

        // 배경 원 그리기
        canvas.drawCircle(cx, cy, radius, backgroundPaint)

        // 시계 방향으로 진행 바 그리기
        val sweepAngle = 360 * progress
        canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius, -90f, sweepAngle, false, progressPaint)
    }

    fun updateProgress(progressFraction: Float) {
        progress = progressFraction
        invalidate()
    }

    fun reset() {
        updateProgress(0f)
    }

    fun getProgress(): Float {
        return progress
    }
    fun setProgressColor(color: Int) {
        progressPaint.color = color
        invalidate()
    }
}
