package com.cyder.android.syncpod.model

data class PublishingSettingItem(
        val id: Id,
        val title: String,
        val description: String
) {
    enum class Id {
        PUBLIC,
        PRIVATE
    }
}
