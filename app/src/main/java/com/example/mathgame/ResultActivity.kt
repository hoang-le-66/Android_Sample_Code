package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    lateinit var btnPlayAgain :Button
    lateinit var btnExit :Button
    lateinit var tvResult :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        btnPlayAgain=findViewById(R.id.btnPlayAgain)
        btnExit=findViewById(R.id.btnExit)
        tvResult=findViewById(R.id.tvResult)

        val score = intent.getIntExtra("score",0)
        tvResult.text = "Your score:"+ score

        btnPlayAgain.setOnClickListener {
            val intent = Intent(this@ResultActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Standard code to close an application
        btnExit.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}