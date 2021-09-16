package com.example.musicapp.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.musicapp.R

class CircleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var radius = 0.0f
    private var bitmap = BitmapFactory.decodeResource(resources, R.drawable.nature1)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.shader = createShader()
        canvas?.drawCircle(radius, radius, radius, paint)
    }

    override fun setImageResource(resId: Int) {
        bitmap = BitmapFactory.decodeResource(
            resources,
            resId
        )

    }

    private fun createShader(): Shader {
        val bitmap =
            Bitmap.createScaledBitmap(this.bitmap, (radius * 2).toInt(), (radius * 2).toInt(), true)
        return BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (minOf(w, h) / 2.0).toFloat()
    }
}