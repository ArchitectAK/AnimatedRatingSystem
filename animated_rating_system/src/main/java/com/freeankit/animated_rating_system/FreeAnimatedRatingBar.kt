package com.freeankit.animated_rating_system

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com)
 * @date 06/09/2017.
 */
class FreeAnimatedRatingBar @kotlin.jvm.JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), IAnimatedRatingBar {

    private var listener: OnRatingChangedListener? = null
    private var duration = 500
    private var seekable = true

    private var progressImage: Drawable? = null
    private var secondaryProgressImage: Drawable? = null
    private var numStars: Int = 0
    private var rating: Float = 0.toFloat()
    private var max: Int = 0
    private var gapSize: Int = 0

    private var items: Array<AnimatedRatingBarItem?>? = null
    private var isNeedRedraw = true

    init {
        orientation = HORIZONTAL
        setAttrs(context, attrs)
        setWillNotDraw(false)
    }

    private fun setAttrs(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }

        val ta = context.obtainStyledAttributes(attrs, R.styleable.AnimatedRatingBar)
        val progressImageResource = ta.getResourceId(R.styleable.AnimatedRatingBar_progressImage, R.id.progress_image)
        val secondaryProgressImageResource = ta.getResourceId(R.styleable.AnimatedRatingBar_secondaryProgressImage, R.id.secondary_progress_image)

        if (progressImageResource != 0) {
            progressImage = resources.getDrawable(progressImageResource)
        }

        if (secondaryProgressImageResource != 0) {
            secondaryProgressImage = resources.getDrawable(secondaryProgressImageResource)
        }


        numStars = ta.getInt(R.styleable.AnimatedRatingBar_numStars, 5)
        max = ta.getInt(R.styleable.AnimatedRatingBar_max, 5)
        rating = ta.getFloat(R.styleable.AnimatedRatingBar_rating, 2.5f)
        gapSize = ta.getDimensionPixelOffset(R.styleable.AnimatedRatingBar_gapSize, 20)

        ta.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        if (isNeedRedraw) {
            resetItems()
        }

        super.onDraw(canvas)
    }


    private fun resetItems() {
        val itemSize = measuredHeight - paddingBottom - paddingTop
        if (itemSize <= 0) {
            return
        }

        removeAllViews()

        if (items != null) {
            val length = items!!.size
            for (i in 0 until length) {
                items!![i] = null
            }
            items = null
        }

        items = arrayOfNulls(numStars)
        val itemParams = LinearLayout.LayoutParams(itemSize, itemSize)

        val progressStars = numStars * rating / max
        val fillStars = progressStars.toInt()
        val levelStar = progressStars - fillStars


        for (i in 0 until numStars) {
            itemParams.leftMargin = if (i == 0) 0 else gapSize

            val item = AnimatedRatingBarItem(context)
            item.layoutParams = itemParams
            val level = if (i < fillStars) 1F else if (i == fillStars) levelStar else 0F
            item.setProgressLevel(level)
            item.setProgressImageDrawable(progressImage)
            item.setSecondaryProgressImageDrawable(secondaryProgressImage)

            items!![i] = item
            addView(items!![i])
        }

        isNeedRedraw = false
    }

    override fun setProgressImageResource(resourceId: Int) {
        if (resourceId == 0) {
            return
        }

        val drawable = resources.getDrawable(resourceId)
        setProgressImageDrawable(drawable)
    }

    override fun setProgressImageDrawable(drawable: Drawable) {
        this.progressImage = drawable
        isNeedRedraw = true
        postInvalidate()
    }

    override fun setSecondaryProgressImageResource(resourceId: Int) {
        if (resourceId == 0) {
            return
        }

        val drawable = resources.getDrawable(resourceId)
        setSecondaryProgressImageDrawable(drawable)
    }

    override fun setSecondaryProgressImageDrawable(drawable: Drawable) {
        this.secondaryProgressImage = drawable
        isNeedRedraw = true
        postInvalidate()
    }

    override fun setNumStars(stars: Int) {
        if (this.numStars == stars) {
            return
        }

        this.numStars = stars
        isNeedRedraw = true
        postInvalidate()
    }

    override fun setRating(rating: Float) {
        var rating = rating
        if (this.rating == rating) {
            return
        }

        if (max < rating) {
            rating = max.toFloat()
        }

        this.rating = rating
        isNeedRedraw = true
        postInvalidate()
    }

    override fun setStarGap(gapSize: Int) {
        if (this.gapSize == gapSize) {
            return
        }

        this.gapSize = gapSize
        isNeedRedraw = true
        postInvalidate()
    }


    override fun setOnRatingChangedListener(listener: OnRatingChangedListener) {
        this.listener = listener
    }


    override fun setSeekable(seekable: Boolean) {
        this.seekable = seekable
    }


    override fun setAnimateDuration(duration: Int) {
        this.duration = duration
    }


    override fun startAnimate() {
        var delay = 0
        for (item in items!!) {
            postDelayed({
                val animator = ObjectAnimator.ofFloat(item, "alpha", 1f, 1f)
                animator.duration = duration.toLong()
                animator.addUpdateListener { animation ->
                    val fraction = animation.animatedFraction
                    item?.rotationY = fraction * 3f * 360f % 360
                }
                animator.start()
            }, delay.toLong())

            delay += duration / items?.size!!
        }
    }

}