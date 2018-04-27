package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivitySearchVideoBinding
import com.cyder.atsushi.youtubesync.databinding.ItemSearchVideoBinding
import com.cyder.atsushi.youtubesync.view.adapter.BindingHolder
import com.cyder.atsushi.youtubesync.view.adapter.ObservableListAdapter
import com.cyder.atsushi.youtubesync.viewmodel.SearchVideoActivityViewModel
import com.cyder.atsushi.youtubesync.viewmodel.SearchVideoViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/23.
 */

class SearchVideoActivity : BaseActivity() {
    @Inject lateinit var viewModel: SearchVideoActivityViewModel
    private lateinit var binding: ActivitySearchVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_video)
        binding.viewModel = viewModel
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter= SearchResultAdapter(viewModel.videoViewModels)
        binding.resultList.adapter = adapter
        binding.resultList.layoutManager = LinearLayoutManager(this)
    }

    inner class SearchResultAdapter(list: ObservableList<SearchVideoViewModel>) : ObservableListAdapter<SearchVideoViewModel, BindingHolder<ItemSearchVideoBinding>>(list) {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemSearchVideoBinding>  = BindingHolder(parent, R.layout.item_search_video)

        override fun onBindViewHolder(holder: BindingHolder<ItemSearchVideoBinding>?, position: Int) {
            val viewModel = getItem(position)
            val binding = holder?.binding
            binding?.viewModel = viewModel
        }

    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SearchVideoActivity::class.java)
    }
}