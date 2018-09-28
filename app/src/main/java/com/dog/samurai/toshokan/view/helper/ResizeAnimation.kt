package com.dog.samurai.toshokan.view.helper

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ResizeAnimation(var view: View, addHeight: Int, startHeight: Int) : Animation() {

    var addHeight = -1
    var startHeight = -1

    init {
        this.addHeight = addHeight
        this.startHeight = startHeight
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val newHeight = (startHeight + addHeight * interpolatedTime).toInt()
        view.layoutParams.height = newHeight
        view.requestLayout()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}