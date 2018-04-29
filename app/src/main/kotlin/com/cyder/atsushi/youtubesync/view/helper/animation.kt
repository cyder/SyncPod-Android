package com.cyder.atsushi.youtubesync.view.helper

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class ResizeAnimation(var view: View, private val addHeight: Int, var startHeight: Int) : Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newHeight = (startHeight + addHeight * interpolatedTime).toInt()
        view.layoutParams.height = newHeight
        view.requestLayout()
    }

    override fun willChangeBounds() = true
}
