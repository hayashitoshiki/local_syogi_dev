package com.example.local_syogi.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    // 初期設定
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide) as Animation
        val fadeDelay: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new) as Animation
        val fadeIn: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_delay) as Animation
        val main = activity as MainActivity

        game_button.startAnimation(fade)
        game_button.visibility = View.VISIBLE
        setting_button.startAnimation(fadeDelay)
        setting_button.visibility = View.VISIBLE
        recordButton.startAnimation(fadeDelay)
        recordButton.visibility = View.VISIBLE
        title.startAnimation(fadeIn)
        title.visibility = View.VISIBLE

        game_button.setOnClickListener {
            closeFragment()
            main.gameSet()
        }
        setting_button.setOnClickListener {
            closeFragment()
            main.account()
        }
        recordButton.setOnClickListener {
            closeFragment()
            main.record()
        }
    }

    // Fragmentを閉じる
    private fun closeFragment() {

        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide) as Animation
        val fadeOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out) as Animation

        setting_button.startAnimation(fade)
        setting_button.visibility = View.INVISIBLE
        game_button.startAnimation(fade)
        game_button.visibility = View.INVISIBLE
        recordButton.startAnimation(fade)
        recordButton.visibility = View.INVISIBLE
        title.startAnimation(fadeOut)
        title.visibility = View.INVISIBLE
    }

    companion object {

        @JvmStatic
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            return fragment
        }
    }
}