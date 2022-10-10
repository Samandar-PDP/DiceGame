package com.example.simplegames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Confetti
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var tvScore1: TextView
    private lateinit var tvScore2: TextView
    private lateinit var spinner: Spinner
    private lateinit var refreshBtn: ImageView
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var im1: ImageView
    private lateinit var im2: ImageView
    private lateinit var textScore: TextView
    private lateinit var textFinish: TextView
    private lateinit var confetti: KonfettiView

    private var allSum1: Int = 0
    private var allSum2: Int = 0

    private val array = arrayOf(100, 200, 300)
    private var checkedItem = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        tvScore1 = findViewById(R.id.tvScore1)
        tvScore2 = findViewById(R.id.tvScore2)
        spinner = findViewById(R.id.spinner)
        refreshBtn = findViewById(R.id.refreshBtn)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        im1 = findViewById(R.id.img1)
        im2 = findViewById(R.id.img2)
        textScore = findViewById(R.id.textScore)
        textFinish = findViewById(R.id.textView2)
        confetti = findViewById(R.id.konfetti)

        gamePlay()

    }

    private fun gamePlay() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.anim)

        refreshBtn.setOnClickListener {
            if(allSum1 > 0 || allSum2 > 0) {
                AlertDialog.Builder(this).apply {
                    setTitle("Do you want to restart this game?")
                    setPositiveButton("Yes") { di, _ ->
                        di.dismiss()
                        clearGame()
                    }
                    setNegativeButton("Cancel", null)
                }.create().show()
            }
        }

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                textFinish.text = array[pos].toString()
                checkedItem = array[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        textFinish.text = spinner.selectedItem.toString()

        btn1.setOnClickListener {
            btn2.isEnabled = true
            btn1.isEnabled = false
            var random1 = (1..6).random()
            var random2 = (1..6).random()
            im1.setImageResource(randomImage(random1))
            im2.setImageResource(randomImage(random2))
            allSum1 += random1 + random2
            textScore.text = "${random1.plus(random2)}"

            im1.startAnimation(anim)
            im2.startAnimation(anim)
            tvScore1.text = allSum1.toString()

            if (allSum1 >= checkedItem) {
                btn1.isEnabled = false
                btn2.isEnabled = false
                showAlertDialog()
            }
            random1 = (1..6).random()
            random2 = (1..6).random()
        }
        btn2.setOnClickListener {
            btn2.isEnabled = false
            btn1.isEnabled = true
            var random1 = (1..6).random()
            var random2 = (1..6).random()

            im1.setImageResource(randomImage(random1))
            im2.setImageResource(randomImage(random2))

            allSum2 += random1 + random2
            textScore.text = "${random1.plus(random2)}"


            im1.startAnimation(anim)
            im2.startAnimation(anim)
            tvScore2.text = allSum2.toString()

            if (allSum2 >= checkedItem) {
                btn1.isEnabled = false
                btn2.isEnabled = false
                showAlertDialog()
            }

            random1 = (1..6).random()
            random2 = (1..6).random()
        }
    }

    @DrawableRes
    private fun randomImage(random: Int): Int {
        return when (random) {
            1 -> R.drawable.dice1
            2 -> R.drawable.dice2
            3 -> R.drawable.dice3
            4 -> R.drawable.dice4
            5 -> R.drawable.dice5
            6 -> R.drawable.dice6
            else -> R.drawable.dice1
        }
    }

    private fun showAlertDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(view)
        val button: MaterialButton = view.findViewById(R.id.textOk)
        val textView: TextView = view.findViewById(R.id.textView)
        textView.text = if (allSum1 > allSum2) "Player 1 win!" else "Player 2 win!"
        button.setOnClickListener {
            alertDialog.dismiss()
            clearGame()
        }

        val party = Party(
            speed = 0f,
            maxSpeed = 70f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 1000, TimeUnit.MILLISECONDS).max(1000),
            position = Position.Relative(0.5, 0.3)
        )
        confetti.start(party)

        alertDialog.show()
    }
    private fun clearGame() {
        allSum1 = 0
        allSum2 = 0
        tvScore1.text = allSum1.toString()
        tvScore2.text = allSum2.toString()
        textScore.text = ""
        btn1.isEnabled = true
        btn2.isEnabled = true
        im1.setImageResource(R.drawable.dice1)
        im2.setImageResource(R.drawable.dice1)
    }
}