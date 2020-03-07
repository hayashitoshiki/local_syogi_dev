package com.example.local_syogi.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.ActivityOptions
import android.widget.Button
import android.widget.TextView
import com.example.local_syogi.R
import android.util.Pair as UntilPa

class MainActivity : AppCompatActivity() {

    lateinit var button:Button
    lateinit var title:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button2)
        title = findViewById(R.id.title)
    }

    fun game(v: View) {
//        val view: ConstraintLayout = findViewById(R.id.mainLayout)
//        val inAnimation = (AnimationUtils.loadAnimation(this, R.anim.fade) as Animation)
//        view.startAnimation(inAnimation)
//        view.setVisibility(view.INVISIBLE)

        val bundle = ActivityOptions.makeSceneTransitionAnimation(this,
            UntilPa.create<View, String>(title, "title"),
            UntilPa.create<View, String>(button,"share")
            ).toBundle()
        startActivity(Intent(this, SettingActivity::class.java), bundle)

//        val i = Intent(this, SettingActivity::class.java)
//        startActivity(i)
    }

}
