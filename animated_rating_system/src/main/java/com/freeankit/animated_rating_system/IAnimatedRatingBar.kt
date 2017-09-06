package com.freeankit.animated_rating_system

import android.graphics.drawable.Drawable


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com)
 * @date 06/09/2017.
 */
interface IAnimatedRatingBar {
    fun setProgressImageResource(resourceId: Int)


    fun setProgressImageDrawable(drawable: Drawable)


    fun setSecondaryProgressImageResource(resourceId: Int)


    fun setSecondaryProgressImageDrawable(drawable: Drawable)


    fun setNumStars(stars: Int)


    fun setRating(rating: Float)


    fun setStarGap(gapSize: Int)


    fun setOnRatingChangedListener(listener: OnRatingChangedListener)


    fun setSeekable(seekable: Boolean)


    fun setAnimateDuration(duration: Int)


    fun startAnimate()
}