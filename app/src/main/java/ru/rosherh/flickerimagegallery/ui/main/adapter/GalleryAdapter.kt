package ru.rosherh.flickerimagegallery.ui.main.adapter

import android.content.Context
import android.view.View
import ru.rosherh.flickerimagegallery.core.adapter.BaseRecyclerAdapter
import ru.rosherh.flickerimagegallery.network.ImageDownloader
import ru.rosherh.flickerimagegallery.network.models.Photo
import ru.rosherh.flickerimagegallery.ui.main.holder.GalleryItemHolder

class GalleryAdapter(
        private val context: Context,
        private val loader: ImageDownloader<GalleryItemHolder>
) : BaseRecyclerAdapter<GalleryItemHolder, Photo>() {

    override fun bind(holder: GalleryItemHolder, position: Int) {
        val item = getData()[position]
        with(holder) {
            progress.visibility = View.VISIBLE
            thumbnail.visibility = View.GONE
            item?.url?.let {
                with(loader) {
                    setThumbnail(holder, it)
                }
            }
        }
    }

    override fun getHolder() = GalleryItemHolder.create(context)

}