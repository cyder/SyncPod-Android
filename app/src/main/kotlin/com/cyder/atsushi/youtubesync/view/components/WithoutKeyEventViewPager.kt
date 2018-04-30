package com.cyder.atsushi.youtubesync.view.components

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.KeyEvent

class WithoutKeyEventViewPager(context: Context, attributeSet: AttributeSet): ViewPager(context, attributeSet) {
    override fun executeKeyEvent(event: KeyEvent) = false
}