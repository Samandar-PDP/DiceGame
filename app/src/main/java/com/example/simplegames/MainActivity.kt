package com.example.simplegames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var tvScore1: TextView
    private lateinit var tvScore2: TextView
    private lateinit var spinner: Spinner
    private lateinit var refreshBtn: ImageView
    private lateinit var btn1: MaterialButton
    private lateinit var btn2: MaterialButton
    private lateinit var im1: ImageView
    private lateinit var im2: ImageView
    private lateinit var textScore: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        tvScore1 = findViewById(R.id.tvScore1)
        tvScore2 = findViewById(R.id.tvScore2)
        spinner = findViewById(R.id.spinner)
        refreshBtn = findViewById(R.id.refreshBtn)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        im1 = findViewById(R.id.img1)
        im2 = findViewById(R.id.img2)
        textScore = findViewById(R.id.textScore)

        gamePlay()

    }
    private fun gamePlay() {
        btn2.isEnabled = false
        val anim = AnimationUtils.loadAnimation(this, R.anim.anim)
    }
}