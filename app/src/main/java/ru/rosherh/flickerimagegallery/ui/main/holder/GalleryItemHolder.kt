package ru.rosherh.flickerimagegallery.ui.main.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import ru.rosherh.flickerimagegallery.R
import ru.rosherh.flickerimagegallery.core.ui.BaseHolder

class GalleryItemHolder(item: View) : BaseHolder(item) {

    val thumbnail: ImageView = item.findViewById(R.id.thumbnail)
    val progress: ProgressBar = item.findViewById(R.id.progress)

    companion object {
        fun create(context: Context) = GalleryItemHolder(LayoutInflater.from(context).inflate(R.layout.gallery_item_holder, null))
    }

    override fun onClick(p0: View?) {

    }

}