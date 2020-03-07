package com.example.local_syogi.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.local_syogi.R

class MattingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matting)
    }

    fun next(v:View){
        val intent = Intent(this,GameRateActivity::class.java)
        startActivity(intent)
        finish()
    }


}