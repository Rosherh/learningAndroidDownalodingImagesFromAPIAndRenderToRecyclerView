package ru.rosherh.flickerimagegallery.ui.main.fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_gallery_fragments.*
import ru.rosherh.flickerimagegallery.R
import ru.rosherh.flickerimagegallery.core.ui.BaseFragment
import ru.rosherh.flickerimagegallery.network.ImageDownloader
import ru.rosherh.flickerimagegallery.network.TestFlickFetcher
import ru.rosherh.flickerimagegallery.network.models.Photos
import ru.rosherh.flickerimagegallery.ui.main.adapter.GalleryAdapter
import ru.rosherh.flickerimagegallery.ui.main.holder.GalleryItemHolder

class GalleryFragments : BaseFragment() {

    private val exec by lazy { requestImages() }
    private lateinit var galleryAdapter: GalleryAdapter
    private val handler by lazy { Handler() }
    private val downloader: ImageDownloader<GalleryItemHolder> = ImageDownloader(
            outHandler = handler,
            onThumbnailDownloaded = { view, bitmap ->
                with(view) {
                    thumbnail.setImageBitmap(bitmap)
                    thumbnail.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            }
    )

    companion object {
        fun getInstance() = GalleryFragments()
    }

    override fun getLayoutId() = R.layout.fragment_gallery_fragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(downloader) {
            start()
            looper
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        downloader.quit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryAdapter = GalleryAdapter(context!!, downloader)
        with(recycler) {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
        }
        exec.execute()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exec.cancel(true)
    }

    @SuppressLint("StaticFieldLeak")
    private fun requestImages() = object : AsyncTask<Void, Void?, String?>() {

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                return TestFlickFetcher().getUrlString()
            } catch (e: Exception) {
                Log.d("App", e.message)
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            result?.let {
                val model: Photos = Gson().fromJson(it, Photos::class.java)
                galleryAdapter.setData(model.photos.photo)
            }
        }

    }

}
