package com.example.puregains_app

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.LinkedTreeMap

data class FeedItem(
    val image : Int,
    val video : Int,
    val photo : Int,
    val name : String,
    val username : String,
    val message : String,
    val viewType : Int,
    val likes : Int,
    val tags : String,
    val post_id : String
) {
        constructor(image : Int, name : String, username : String, message : String, likes : Int, tags : String, post_id : String) : this (
                image,
                -1,
                -1,
                name,
                username,
                message,
                FeedItemAdapter.TYPE_TEXT,
                likes,
                tags,
                post_id
        )

        constructor(viewType: Int) : this (
                -1,
                -1,
                -1,
                "null",
                "null",
                "null",
                viewType,
                0,
                "null",
                "null"
        )

        constructor(image : Int, video : Int, name : String, username : String, message : String, likes : Int, tags : String, post_id : String) : this (
                image,
                video,
                -1,
                name,
                username,
                message,
                FeedItemAdapter.TYPE_VIDEO,
                likes,
                tags,
                post_id
        )
        constructor(image : Int, name : String, username : String, message : String, picture : Int, likes : Int, tags : String, post_id : String) : this (
                image,
                -1,
                picture,
                name,
                username,
                message,
                FeedItemAdapter.TYPE_PHOTO,
                likes,
                tags,
                post_id
        )

    companion object {
            fun generateItem(
                    post : LinkedTreeMap<String, Any>,
                    resource: Resources,
                    packageName: String
            ) : FeedItem {
                    val photo = post.get("photo_path")
                    val video = post.get("video_path")
                    val name = post.get("name").toString()
                    val message = post.get("message").toString()
                    val post_tags = post.get("post_tags").toString()
                    val likes = post.get("likes").toString().toDouble().toInt()
                    val post_id = post.get("post_id").toString()
                    val username = post.get("username").toString()
                    val avatar = post.get("avatar_path").toString()

                    val item : FeedItem
                    if (photo != null) {
                            item = FeedItem(
                                    toResID(avatar, packageName, resource),
                                    name,
                                    username,
                                    message,
                                    toResID(photo.toString(), packageName, resource),
                                    likes,
                                    post_tags,
                                    post_id
                            )
                    } else if (video != null) {
                            item = FeedItem(
                                    toResID(avatar, packageName, resource),
                                    toResID(video.toString(), packageName, resource),
                                    name,
                                    username,
                                    message,
                                    likes,
                                    post_tags,
                                    post_id
                            )
                    } else {
                            item = FeedItem(
                                    toResID(avatar, packageName, resource),
                                    name,
                                    username,
                                    message,
                                    likes,
                                    post_tags,
                                    post_id
                            )
                    }

                    return item
            }

            fun toResID(
                    path : String,
                    packageName : String,
                    resource : Resources
            ) : Int {
                    return resource.getIdentifier(path , "raw", packageName);
            }
    }
}