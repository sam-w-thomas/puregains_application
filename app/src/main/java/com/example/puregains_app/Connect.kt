package com.example.puregains_app

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import okhttp3.*
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException


const val IP_ADDRESS = "10.0.2.2"
const val PORT_NUMBER = "5000"
const val SERVER_URL = "http://" + IP_ADDRESS + ":" + PORT_NUMBER + "/"

/**
 * Connect to server
 *
 * params:
 * - postUrl: URL to send post
 * - postBody: HTTP body to send
 */

fun postText() {

}


fun postMedia(
    media_path: String
) {

}

fun postPhoto(selectedImg: String) {
    val file : File = File(selectedImg)
    val array : ByteArray = byteArrayEncode(file)

    Log.i("TEST", array.toString())
}

fun byteArrayEncode(file: File) : ByteArray {
    return FileUtils.readFileToByteArray(file)
}

fun postRequest(url: String, body: RequestBody, view: View) {

    val client : OkHttpClient = OkHttpClient()

    val request : Request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
            call?.cancel()

            Log.i("HTTP", e.toString())
        }

        override fun onResponse(call: Call?, response: Response?) {
            Log.i("HTTP", "CALL SUCCED")

            if (response != null) {
                Log.i("HTTP-MESSAGE", response.body().string())
            }
        }
    })
}
