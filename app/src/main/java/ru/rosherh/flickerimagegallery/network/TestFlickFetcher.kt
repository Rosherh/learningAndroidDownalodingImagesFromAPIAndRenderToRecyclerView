package ru.rosherh.flickerimagegallery.network

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class TestFlickFetcher {

    /**
     * Ссылка на данные.
     */
    companion object {
        const val recent = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=1ca604acd3a0ac1f68ca031f455fe2c8&extras=url_s&format=json&nojsoncallback=1&api_sig=560873e5fc9132a5dc1d00c89fc541c4"
    }

    @Throws(IOException::class)
    fun getUrlBytes(urlSpec: String): ByteArray {
        val url = URL(urlSpec)
        val connection = url.openConnection() as HttpURLConnection
        try {
            val out = ByteArrayOutputStream()
            val `in` = connection.inputStream

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException(connection.responseMessage + " with: " + urlSpec)
            }

            val buffer = ByteArray(1024)
            var bytesRead = `in`.read(buffer)
            while (bytesRead > 0) {
                out.write(buffer, 0, bytesRead)
                bytesRead = `in`.read(buffer)
            }
            out.close()

            return out.toByteArray()
        } finally {
            connection.disconnect()
        }
    }

    fun getUrlString(urlSpec: String = recent) = String(getUrlBytes(urlSpec))

}