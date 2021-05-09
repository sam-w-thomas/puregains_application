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

//            fun generateFeed(name: String?) : List<FeedItem> {
//                    val fullList = listOf<FeedItem>(
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    R.raw.video_roll,
//                                    "Joss",
//                                    "joss16!",
//                                    "Heres how to do some FANTASTIC squats"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Joss",
//                                    "joss16!",
//                                    "Heres how to do some FANTASTIC squats",
//                                    R.raw.sam_photo
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Steve",
//                                    "Steve12",
//                                    "Like the gains 24/7 my friends. You gotta do some stretches"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_baby,
//                                    "Bob",
//                                    "Bob7",
//                                    "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Sam",
//                                    "Sam19",
//                                    "GET those quads working and stretching and drop it down low"
//                            ),
//                            FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Sam",
//                                    "sam12",
//                                    "Heres how to do some FANTASTIC squats"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Ross",
//                                    "Ross1923",
//                                    "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Rowland",
//                                    "rowland.19",
//                                    "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Steve",
//                                    "Steve12",
//                                    "Like the gains 24/7 my friends. You gotta do some stretches"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_baby,
//                                    "Bob",
//                                    "Bob7",
//                                    "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Sam",
//                                    "Sam19",
//                                    "GET those quads working and stretching and drop it down low"
//                            ),
//                            FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Ross",
//                                    "Ross1923",
//                                    "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Rowland",
//                                    "rowland.19",
//                                    "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    R.raw.video_squat,
//                                    "Paul",
//                                    "paul1",
//                                    "Heres how to do some FANTASTIC squats, gotta get that booty lookign TOOTY"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Steve",
//                                    "Steve12",
//                                    "Like the gains 24/7 my friends. You gotta do some stretches"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_baby,
//                                    "Bob",
//                                    "Bob7",
//                                    "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Sam",
//                                    "Sam19",
//                                    "GET those quads working and stretching and drop it down low"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    R.raw.video_highleg,
//                                    "Sam",
//                                    "sam1",
//                                    "Heres how to do a sexy highleg"
//                            ),
//                            FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Ross",
//                                    "Ross1923",
//                                    "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Rowland",
//                                    "rowland.19",
//                                    "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Steve",
//                                    "Steve12",
//                                    "Like the gains 24/7 my friends. You gotta do some stretches"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_baby,
//                                    "Bob",
//                                    "Bob7",
//                                    "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Sam",
//                                    "Sam19",
//                                    "GET those quads working and stretching and drop it down low"
//                            ),
//                            FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
//                            FeedItem(
//                                    R.drawable.avatar_man,
//                                    "Ross",
//                                    "Ross1923",
//                                    "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
//                            ),
//                            FeedItem(
//                                    R.drawable.avatar_android,
//                                    "Rowland",
//                                    "rowland.19",
//                                    "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
//                            )
//                    )
//
//                    //Basic temporary feed generation
//                    return if(name == null) fullList else fullList.filter { it.name == name }
//            }

            fun toResID(
                    path : String,
                    packageName : String,
                    resource : Resources
            ) : Int {
                    return resource.getIdentifier(path , "raw", packageName);
            }
    }
}