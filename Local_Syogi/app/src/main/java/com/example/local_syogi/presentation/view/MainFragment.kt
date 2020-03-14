package com.example.local_syogi.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R


class MainFragment: Fragment() {

    private lateinit var gameButton: Button
    private lateinit var settingButton: Button
    private lateinit var title: TextView

    //初期設定
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        gameButton = view.findViewById(R.id.game_button)
        settingButton = view.findViewById(R.id.setting_button)
        title = view.findViewById(R.id.title)

        gameButton.setOnClickListener{
            closeFragment()
        }
        settingButton.setOnClickListener{
            closeFragment()
        }
        return view
    }

    //Fragment表示アニメーション
    override fun onStart() {
        super.onStart()
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new) as Animation
        val fadeIn: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in) as Animation

        gameButton.startAnimation(fade)
        gameButton.visibility = View.VISIBLE
        settingButton.startAnimation(fade)
        settingButton.visibility = View.VISIBLE
        title.startAnimation(fadeIn)
        title.visibility = View.VISIBLE

    }


    //Fragmentを閉じる
    private fun closeFragment(){
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide) as Animation
        val fadeOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out) as Animation

        settingButton.startAnimation(fade)
        settingButton.visibility = View.INVISIBLE
        gameButton.startAnimation(fade)
        gameButton.visibility = View.INVISIBLE
        title.startAnimation(fadeOut)
        title.visibility = View.INVISIBLE

       parentActivity.gameSet()
    }

    companion object {

        private lateinit var parentActivity:MainActivity

        @JvmStatic
        fun newInstance(activity:MainActivity): MainFragment {
            val fragment = MainFragment()
            parentActivity = activity
            return fragment
        }
    }

}