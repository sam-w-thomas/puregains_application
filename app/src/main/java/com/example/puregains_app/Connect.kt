package com.example.puregains_app

import android.app.Activity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import kotlin.Exception

const val IP_ADDRESS = "10.0.2.2"
const val PORT_NUMBER = "5000"
const val SERVER_URL = "http://" + IP_ADDRESS + ":" + PORT_NUMBER + "/"
private val mediaType : MediaType = MediaType.parse("application/json")

class Connect() {
    companion object {
        /**
         * Update likes associated with post
         */
        fun updateLikes(
            post_id : String,
            activity: Activity
        ) : String {
            val client: OkHttpClient = OkHttpClient()

            val request : Request = Request.Builder()
                .url(SERVER_URL + "api/post/" + post_id + "/likes")
                .put(RequestBody.create(null, ""))
                .addHeader("x-access-tokens", Auth.getToken(activity))
                .build()

            val response = client.newCall(request).execute()

            if (response.code() != 200) {
                throw Exception("Unable to update likes")
            } else {
                return JSONObject(response.body().string().toString()).get("post_likes").toString()
            }
        }

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
            } else if ((media_path=="") or (media_path=="null")) {
                body = RequestBody.create(
                    mediaType,
                    JsonObject(
                        mapOf(
                            "username" to JsonPrimitive(Auth.getUsername(activity)),
                            "message" to JsonPrimitive(message),
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
            val gson = Gson()

            val return_json = gson.fromJson(json_str, List::class.java)
            return return_json as List<LinkedTreeMap<String, Any>>
        }

        /**
         * Retrieves user info
         */
        fun getUser(
        username : String
        ): Map<String,Any> {
            val client: OkHttpClient = OkHttpClient()

            val request = Request.Builder()
                    .url(SERVER_URL + "api/user/" + username)
                    .get()
                    .build()

            val response = client.newCall(request).execute()

            if(response.code() != 200) {
                throw Exception("Unable to get user information")
            }

            val json_str = response.body().string().toString()
            val gson = Gson()

            return gson.fromJson(json_str, Map::class.java) as Map<String,Any>
        }

        /**
         * Update user
         */
        fun updateUser(
            name : String,
            desc : String,
            tags : String,
            avatar : String,
            username : String,
            token : String
        ) : Boolean {
            val client: OkHttpClient = OkHttpClient()

            val properties = mutableMapOf<String, JsonPrimitive>()

            // build properties
            if (name != "") {
                properties["name"] = JsonPrimitive(name)
            }
            if (desc != "") {
                properties["desc"] = JsonPrimitive(desc)
            }
            if (tags != "") {
                properties["tags"] = JsonPrimitive(tags)
            }
            if (avatar != "") {
                properties["avatar_path"] = JsonPrimitive(avatar)
            }

            val body = RequestBody.create(
                mediaType,
                JsonObject(properties).toString()
            )

            val request = Request.Builder()
                .url(SERVER_URL + "api/user/" + username)
                .put(body)
                .addHeader("x-access-tokens",token)
                .build()

            lateinit var response : Response
            runBlocking(Dispatchers.IO) {
                response = client.newCall(request).execute()
            }

            val success : Boolean
            if (response.code() == 200) {
                success = true
            } else {
                success = false
            }

            return success
        }
    }
}

