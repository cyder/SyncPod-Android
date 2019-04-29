package com.cyder.android.syncpod.view.components

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.KeyEvent

class WithoutKeyEventViewPager(context: Context, attributeSet: AttributeSet): ViewPager(context, attributeSet) {
    override fun executeKeyEvent(event: KeyEvent) = false
}