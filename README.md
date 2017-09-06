# AnimatedRatingSystem


![GIF](https://github.com/AnkitDroidGit/AnimatedRatingSystem/blob/master/app/art/gif.gif)

Use maven repo and add it to project level build.gradle inside dependencies
 
    maven {
  	 url 'https://dl.bintray.com/ankitdroiddeveloper/FreeAnimatedRatingBar'
  	}

Add dependency to app level build.gradle 

    compile 'com.freeankit.animated_rating_system:animated_rating_system:1.0.3'

And use free animated rating bar as below 

In XML 


      <com.freeankit.animated_rating_system.FreeAnimatedRatingBar
             android:id="@+id/arb"
             android:layout_width="wrap_content"
             android:layout_height="30dp"
             android:layout_centerHorizontal="true"
             android:layout_marginTop="100dp"
             app:gapSize="10dp"
             app:max="5"
             app:numStars="5"
             app:progressImage="@drawable/icon_progress"
             app:rating="4.3"
             app:secondaryProgressImage="@drawable/icon_secondary" />
             
             
In Java/Kotlin activity


     val arb = findViewById<FreeAnimatedRatingBar>(R.id.arb) as FreeAnimatedRatingBar
        arb.setSeekable(true)
        arb.setNumStars(7)
        // arb.setStarGap(2)
        findViewById<Button>(R.id.btn).setOnClickListener({
            arb.startAnimate()
        })
        
See code for more.. Happy Coding !!!!