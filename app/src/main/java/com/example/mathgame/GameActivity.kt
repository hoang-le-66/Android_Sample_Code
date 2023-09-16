package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    lateinit var tvScore : TextView
    lateinit var tvLife : TextView
    lateinit var tvTime : TextView

    lateinit var tvQuestion : TextView
    lateinit var edtAnswer : EditText

    lateinit var btnOK : Button
    lateinit var btnNext : Button
    //CountDownTimer is an abstract class. Normally, you cant create an object directyly from an abstract class
    //But, we don't encounter any errors so let see it later
    lateinit var timer: CountDownTimer
    private val startTimerInMillis : Long = 15000
    var timeLeftInMillis : Long = startTimerInMillis
    //Correct answer variable be a global var
    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        supportActionBar!!.title = "Addition"

        tvScore = findViewById(R.id.tvScore)
        tvLife = findViewById(R.id.tvLife)
        tvTime = findViewById(R.id.tvTime)
        tvQuestion = findViewById(R.id.tvQuestion)
        edtAnswer = findViewById(R.id.edtAnswer)
        btnOK = findViewById(R.id.btnOK)
        btnNext = findViewById(R.id.btnNext)

        gameContinue()

        btnOK.setOnClickListener {
            val input = edtAnswer.text.toString()
            if (input == ""){
                Toast.makeText(applicationContext,"Answer box empty!!",Toast.LENGTH_LONG)
                    .show()
            }else{
                val userAnswer = input.toInt()

                if(userAnswer == correctAnswer){
                    userScore += 10
                    tvQuestion.text = "Congratulations, your answer correct!!"
                    tvScore.text = userScore.toString()
                }else{
                    pauseTimer()
                    userLife--
                    tvQuestion.text = "Your answer was wrong!!"
                    tvLife.text = userLife.toString()
                }
            }
        }

        btnNext.setOnClickListener {
            resetTimer()
            edtAnswer.setText("")
            if (userLife==0){
                Toast.makeText(applicationContext,"Game over!!",Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this@GameActivity,ResultActivity::class.java)
                //Send the userScore to Result Page
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            }else{
                gameContinue()
            }
        }
    }

    fun gameContinue(){
       val number1 =  Random.nextInt(0,100)
       val number2 =  Random.nextInt(0,100)

        tvQuestion.text = "$number1 + $number2"

        correctAnswer = number1 + number2

        startTimer()
    }

    fun startTimer(){
        //Error: Cannot create an instance of an abstract class
        timer = object : CountDownTimer(timeLeftInMillis,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                //What you want to do when the time finished.
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                tvLife.text=userLife.toString()
                tvQuestion.text = "Time is up!!"
            }
        }.start()
    }

    fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
    }

    fun pauseTimer() {
        timer.cancel()
    }

    fun updateText() {
        val remainingTime : Int = (timeLeftInMillis/1000).toInt()
        tvTime.text = String.format(Locale.getDefault(),"%02d",remainingTime)
    }


}