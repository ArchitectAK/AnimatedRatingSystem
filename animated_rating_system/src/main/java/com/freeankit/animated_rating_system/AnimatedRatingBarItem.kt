package com.freeankit.animated_rating_system

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com)
 * @date 06/09/2017.
 */
class AnimatedRatingBarItem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var ivProgress: ClipableImageView? = null
    private var ivSecondaryProgress: ImageView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.animated_rating_bar_item, this)
        ivProgress = findViewById<View>(R.id.progress_image) as ClipableImageView
        ivSecondaryProgress = findViewById<View>(R.id.secondary_progress_image) as ImageView?
    }


    fun setProgressImageDrawable(drawable: Drawable?) {
        ivProgress?.setImageDrawable(drawable)
    }


    fun setSecondaryProgressImageDrawable(drawable: Drawable?) {
        ivSecondaryProgress?.setImageDrawable(drawable)
    }

    fun setProgressLevel(level: Float) {
        ivProgress?.setLevel(level)
    }
}