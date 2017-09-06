package com.freeankit.animatedratingsystem

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.freeankit.animated_rating_system.FreeAnimatedRatingBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val arb = findViewById<FreeAnimatedRatingBar>(R.id.arb) as FreeAnimatedRatingBar
        arb.setSeekable(true)
        // arb.setRating(80F)
        arb.setNumStars(7)
//        arb.setStarGap(2)

        findViewById<Button>(R.id.btn).setOnClickListener({
            arb.startAnimate()
        })
    }
}