package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import android.util.Log
import android.view.inputmethod.EditorInfo
import com.cyder.atsushi.youtubesync.repository.YouTubeRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/23.
 */

class SearchVideoActivityViewModel @Inject constructor(
        private val navigator: Navigator,
        private val repository: YouTubeRepository
) : ActivityViewModel() {
    var searchWord: ObservableField<String> = ObservableField()
    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    fun onBackButtonClicked() = navigator.closeActivity()

    fun onEnterKeyClicked(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val word = searchWord.get() ?: ""
            repository.getYouTubeSearch(word)
                    .subscribe { result, _ ->
                        Log.d("TAG", result.toString())
                    }
        }
        return false
    }
}