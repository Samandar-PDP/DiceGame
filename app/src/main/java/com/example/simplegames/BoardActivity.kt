package com.example.simplegames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import java.util.*

class BoardActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var lastDir: Int = 0
    private var isSpinning: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        imageView = findViewById(R.id.imageView)
        playGame()
    }
    private fun playGame() {
        val random = Random()
        imageView.setOnClickListener {
            if (!isSpinning) {
                var newDir = random.nextInt(2000)
                val pivotX = imageView.pivotX
                val pivotY = imageView.pivotY
                val rotate = RotateAnimation(lastDir.toFloat(), newDir.toFloat(), pivotX, pivotY)
                rotate.duration = 2000
                rotate.fillAfter = true
                lastDir = newDir
                imageView.startAnimation(rotate)

                rotate.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                        isSpinning = true
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        isSpinning = false
                    }

                    override fun onAnimationRepeat(p0: Animation?) {}
                })
            }
        }
    }
}