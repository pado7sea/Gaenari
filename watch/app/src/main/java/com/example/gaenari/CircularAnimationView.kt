package com.example.gaenari

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircularAnimationView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var angle = 0f
    private val outerCirclePaint = Paint() // 큰 원용
    private val innerCirclePaint = Paint() // 작은 원용

    init {
        // 큰 원 페인트 (테두리)
        outerCirclePaint.color = Color.BLUE
        outerCirclePaint.strokeWidth = 5f
        outerCirclePaint.style = Paint.Style.STROKE // 테두리만

        // 작은 원 페인트 (꽉 채움)
        innerCirclePaint.color = Color.RED
        innerCirclePaint.style = Paint.Style.FILL // 꽉 찬 원
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas?.let {
            val radius = (width.coerceAtMost(height) / 2).toFloat() - outerCirclePaint.strokeWidth
            val cx = width / 2f
            val cy = height / 2f

            // 큰 원 그리기 (파란색 테두리)
            it.drawCircle(cx, cy, radius, outerCirclePaint)

            // 각도 기반 작은 원 위치 계산
            val x = (cx + radius * Math.cos(Math.toRadians(angle.toDouble()))).toFloat()
            val y = (cy + radius * Math.sin(Math.toRadians(angle.toDouble()))).toFloat()

            // 꽉 찬 작은 원 그리기 (빨간색)
            it.drawCircle(x, y, 10f, innerCirclePaint) // 10픽셀 크기의 작은 원
        }
    }

    fun updateAngle(delta: Float) {
        angle += delta
        invalidate() // 다시 그리기
    }
}
