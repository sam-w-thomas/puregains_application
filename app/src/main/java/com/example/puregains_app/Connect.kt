package com.example.puregains_app

import android.app.Activity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import kotlin.Exception

const val IP_ADDRESS = "10.0.2.2"
const val PORT_NUMBER = "5000"
const val SERVER_URL = "http://" + IP_ADDRESS + ":" + PORT_NUMBER + "/"
private val mediaType : MediaType = MediaType.parse("application/json")

class Connect() {
    companion object {
        fun sendPost(
            message : String,
            tags : String,
            media_path : String,
            is_video : Boolean,
            activity : Activity
        ) {
            val client: OkHttpClient = OkHttpClient()

            val body : RequestBody
            if (is_video) {
                body = RequestBody.create(
                    mediaType,
                    JsonObject(
                        mapOf(
                            "username" to JsonPrimitive(Auth.getUsername(activity)),
                            "message" to JsonPrimitive(message),
                            "video_path" to JsonPrimitive(media_path),
                            "post_tags" to JsonPrimitive(tags)
                        )
                    ).toString()
                )
            } else {
                 body = RequestBody.create(
                    mediaType,
                    JsonObject(
                        mapOf(
                            "username" to JsonPrimitive(Auth.getUsername(activity)),
                            "message" to JsonPrimitive(message),
                            "photo_path" to JsonPrimitive(media_path),
                            "post_tags" to JsonPrimitive(tags)
                        )
                    ).toString()
                )

            }

            val request : Request = Request.Builder()
                .url(SERVER_URL + "api/post")
                .post(body)
                .addHeader("x-access-tokens", Auth.getToken(activity))
                .build()

            client.newCall(request).enqueue( object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    Log.i("POSTS","UNABLE TO CREATE POSTS")
                    throw Exception("Unable to create post")
                }

                override fun onResponse(call: Call?, response: Response?) {
                    Log.i("POST","POST Succesfully created")
                }

            }

            )
        }

        fun getPosts(
            username: String?,
            name: String?,
            tags: String?
        ): List<LinkedTreeMap<String, Any>> {
            val client: OkHttpClient = OkHttpClient()

            lateinit var request: Request
            if (username != null) {
                request = Request.Builder()
                    .url(SERVER_URL + "api/user/" + username + "/posts")
                    .get()
                    .build()
            } else {
                val request_build: Request.Builder = Request.Builder()
                    .url(SERVER_URL + "api/posts")
                    .get()

                if (name != null) {
                    request_build.addHeader("name", name)
                }

                if (tags != null) {
                    request_build.addHeader("tags", tags)
                }

                request = request_build.build()
            }

            val response = client.newCall(request).execute()

            val json_str = response.body().string().toString()

            Log.i("POSTS", json_str)

            val gson = Gson()

            return gson.fromJson(json_str, List::class.java) as List<LinkedTreeMap<String, Any>>
        }
    }
}

