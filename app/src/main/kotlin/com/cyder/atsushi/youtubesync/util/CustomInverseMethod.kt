package com.cyder.atsushi.youtubesync.util

import android.databinding.InverseMethod

object SafeUnbox {
    @JvmStatic
    @InverseMethod("box")
    fun unbox(boxed: Boolean?): Boolean {
        return boxed ?: false
    }

    @JvmStatic
    fun box(unboxed: Boolean): Boolean? {
        return unboxed
    }
}