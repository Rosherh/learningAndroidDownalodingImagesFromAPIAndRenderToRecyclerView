package ru.rosherh.flickerimagegallery.network

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import ru.rosherh.flickerimagegallery.core.ui.BaseHolder
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class ImageDownloader<VTarget : BaseHolder>(
        private val outHandler: Handler,
        val onThumbnailDownloaded: (view: VTarget, bitmap: Bitmap) -> Unit
) : HandlerThread(ImageDownloader::class.java.simpleName) {

    companion object {
        const val DOWNLOAD_MESSAGE = 0
    }

    private lateinit var requestHandler: Handler
    private val map = ConcurrentHashMap<VTarget, String>()

    fun setThumbnail(view: VTarget, url: String) {
        if (url.isNullOrEmpty()) {
            map.remove(view)
        } else {
            map[view] = url
            requestHandler.obtainMessage(DOWNLOAD_MESSAGE, view).sendToTarget()
        }
    }

    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        requestHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                msg?.let {
                    if (it.what == DOWNLOAD_MESSAGE) {
                        val target = it.obj as VTarget
                        handleRequest(target)
                    }
                }
            }
        }
    }

    private fun handleRequest(target: VTarget) {
        try {
            map[target]?.let {
                val byteArray = TestFlickFetcher().getUrlBytes(it)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                outHandler.post {
                    if (map[target] != null) {
                        map.remove(target)
                        onThumbnailDownloaded.invoke(target, bitmap)
                    }
                }
            }
        } catch (e: IOException) {
            Log.d("App", "TEST: error download image - ${e.message}")
        }
    }


}