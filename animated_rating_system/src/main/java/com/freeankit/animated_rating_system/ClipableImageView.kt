package com.freeankit.animated_rating_system

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com)
 * @date 06/09/2017.
 */
class ClipableImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
    private val COLOR_DRAWABLE_DIMENSION = 2


    private var bitmap: Bitmap? = null
    private var bitmapShader: BitmapShader? = null

    private var bitmapHeight = 0
    private var bitmapWidth = 0

    private val bitmapPaint = Paint()
    private val shaderMatrix = Matrix()
    private val drawableRect = RectF()
    private val levelRect = RectF()

    private var level: Float = 0.toFloat()

    fun init() {
        setWillNotDraw(false)
        initializeBitmap()
    }


    private fun initializeBitmap() {
        bitmap = getBitmapFromDrawable(drawable)
        if (bitmap == null) {
            invalidate()
            return
        }

        setup()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        try {
            val bitmap: Bitmap

            if (drawable is ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG)
            } else {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, BITMAP_CONFIG)
            }

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    private fun setup() {
        if (width == 0 && height == 0) {
            return
        }

        if (bitmap == null) {
            invalidate()
            return
        }

        bitmapShader = BitmapShader(bitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        bitmapPaint.setAntiAlias(true)
        bitmapPaint.setShader(bitmapShader)

        bitmapWidth = bitmap!!.width
        bitmapHeight = bitmap!!.height

        drawableRect.set(calculateBounds())
        levelRect.set(calculateBounds())
        levelRect.right = levelRect.left + (levelRect.right - levelRect.left) * level
        updateShaderMatrix()
        invalidate()
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom

        val sideLength = Math.min(availableWidth, availableHeight)

        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f

        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f

        shaderMatrix.set(null)

        if (bitmapWidth * drawableRect.height() > drawableRect.width() * bitmapHeight) {
            scale = drawableRect.height() / bitmapHeight.toFloat()
            dx = (drawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            scale = drawableRect.width() / bitmapWidth.toFloat()
            dy = (drawableRect.height() - bitmapHeight * scale) * 0.5f
        }

        shaderMatrix.setScale(scale, scale)
        shaderMatrix.postTranslate((dx + 0.5f).toInt() + drawableRect.left, (dy + 0.5f).toInt() + drawableRect.top)

        bitmapShader!!.setLocalMatrix(shaderMatrix)
    }


    fun setLevel(level: Float) {
        this.level = level
        levelRect.set(calculateBounds())
        levelRect.right = levelRect.left + (levelRect.right - levelRect.left) * level
        postInvalidate()
    }


    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    protected override fun onDraw(canvas: Canvas) {
        if (measuredHeight == 0 || measuredWidth == 0) {
            return
        }

        canvas.drawRect(levelRect, bitmapPaint)
    }
}