package com.dog.samurai.toshokan.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.dog.samurai.toshokan.FLICKR_URL
import com.dog.samurai.toshokan.R
import com.dog.samurai.toshokan.RESAS_URL
import com.dog.samurai.toshokan.view.helper.ResizeAnimation
import kotlinx.android.synthetic.main.fragment_top.*
import java.io.BufferedReader
import java.io.InputStreamReader


class TopFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    private var isShow = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openSourceText = readText()
        open_source_text.text = openSourceText

        setOpenSourceListener()
        setClickListener()
    }

    private fun readText(): String {

        var returnText = ""
        BufferedReader(InputStreamReader(activity.assets.open("license.txt"))).use {
            var line = it.readLine()
            while (line != null) {
                returnText += "$line\n"
                line = it.readLine()
            }
        }

        return returnText
    }

    private fun setOpenSourceListener() {
        var height = 0
        open_source_text.viewTreeObserver.addOnGlobalLayoutListener {
            if (height == 0) {
                height = open_source_text.height
                if (height != 0) open_source_text.visibility = View.GONE
            }
        }

        open_source_container?.setOnClickListener {
            if (isShow) {
                val rotate = RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 300
                    fillAfter = true
                }
                open_source_chevron.startAnimation(rotate)

                val reduct = ResizeAnimation(open_source_text, -height, height).apply {
                    duration = 500
                    fillAfter = true
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {}

                        override fun onAnimationEnd(p0: Animation?) {
                            open_source_text.visibility = View.GONE
                            isShow = false
                        }

                        override fun onAnimationStart(p0: Animation?) {}
                    })
                }
                open_source_text.startAnimation(reduct)

            } else {
                val rotate = RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 300
                    fillAfter = true
                }
                open_source_chevron.startAnimation(rotate)

                val expand = ResizeAnimation(open_source_text, height, 0).apply {
                    duration = 500
                    fillAfter = true
                }
                open_source_text.startAnimation(expand)
                open_source_text.visibility = View.VISIBLE
                isShow = true

            }
        }
    }

    private fun setClickListener() {
        resas_image.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(RESAS_URL)))
        }

        flickr_image.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(FLICKR_URL)))
        }

        use_button.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                replace(R.id.container, SearchFragment())
                addToBackStack("")
                commit()
            }
        }
    }


}