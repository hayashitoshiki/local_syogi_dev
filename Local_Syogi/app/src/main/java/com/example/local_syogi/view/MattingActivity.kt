package com.example.local_syogi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.local_syogi.R
import com.example.local_syogi.contact.MattingActivityContact

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