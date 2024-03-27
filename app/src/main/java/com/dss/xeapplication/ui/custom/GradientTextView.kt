package com.dss.xeapplication.ui.custom

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.dss.xeapplication.R

class GradientTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    private var change: Boolean = false

    fun clicked() {
        change = true
        paint.shader = LinearGradient(
            0f,
            0f,
            0f,
            height.toFloat(),
            ContextCompat.getColor(context, R.color.color1),
            ContextCompat.getColor(context, R.color.color2),
            Shader.TileMode.CLAMP
        )
        invalidate()
    }

    fun nclicked() {
        paint.shader = LinearGradient(
            0f,
            0f,
            0f,
            height.toFloat(),
            ContextCompat.getColor(context, R.color.color8),
            ContextCompat.getColor(context, R.color.color8),
            Shader.TileMode.CLAMP
        )

        invalidate()
    }
}