package com.example.materialdesignquicklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.materialdesignquicklist.view.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //HomeFrgment追加
        supportFragmentManager
            .beginTransaction()
            .add(R.id.home_root, HomeFragment())
            .addToBackStack(null)
            .commit()
    }
}
