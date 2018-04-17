package com.cyder.atsushi.youtubesync.api.response

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("next_page_token") val nextPageToken: String?,
    @SerializedName("prev_page_token") val prevPageToken: String?,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("results_per_page") val resultsPerPage : Int,
    val items: List<Video>
)