package com.example.puregains_app

import androidx.recyclerview.widget.RecyclerView

data class FeedItem(
    val image : Int,
    val video : Int,
    val photo : Int,
    val name : String,
    val username : String,
    val message : String,
    val viewType : Int
) {
        constructor(image : Int, name : String, username : String, message : String) : this (
                image,
                -1,
                -1,
                name,
                username,
                message,
                FeedItemAdapter.TYPE_TEXT
        )

        constructor(image : Int, video : Int, name : String, username : String, message : String) : this (
                image,
                video,
                -1,
                name,
                username,
                message,
                FeedItemAdapter.TYPE_VIDEO
        )
        constructor(image : Int, name : String, username : String, message : String, picture : Int) : this (
                image,
                -1,
                picture,
                name,
                username,
                message,
                FeedItemAdapter.TYPE_PHOTO
        )

    companion object {
        fun generateFeed(name: String?) : List<FeedItem> {
            val fullList = listOf<FeedItem>(
                    FeedItem(
                            R.drawable.avatar_android,
                            R.raw.video_roll,
                            "Joss",
                            "joss16!",
                            "Heres how to do some FANTASTIC squats"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Joss",
                            "joss16!",
                            "Heres how to do some FANTASTIC squats",
                            R.raw.sam_photo
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Steve",
                            "Steve12",
                            "Like the gains 24/7 my friends. You gotta do some stretches"
                    ),
                    FeedItem(
                            R.drawable.avatar_baby,
                            "Bob",
                            "Bob7",
                            "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
                    ),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Sam",
                            "Sam19",
                            "GET those quads working and stretching and drop it down low"
                    ),
                    FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Sam",
                            "sam12",
                            "Heres how to do some FANTASTIC squats"
                    ),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Ross",
                            "Ross1923",
                            "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Rowland",
                            "rowland.19",
                            "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Steve",
                            "Steve12",
                            "Like the gains 24/7 my friends. You gotta do some stretches"
                    ),
                    FeedItem(
                            R.drawable.avatar_baby,
                            "Bob",
                            "Bob7",
                            "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
                    ),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Sam",
                            "Sam19",
                            "GET those quads working and stretching and drop it down low"
                    ),
                    FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Ross",
                            "Ross1923",
                            "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Rowland",
                            "rowland.19",
                            "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            R.raw.video_squat,
                            "Paul",
                            "paul1",
                            "Heres how to do some FANTASTIC squats, gotta get that booty lookign TOOTY"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Steve",
                            "Steve12",
                            "Like the gains 24/7 my friends. You gotta do some stretches"
                    ),
                    FeedItem(
                            R.drawable.avatar_baby,
                            "Bob",
                            "Bob7",
                            "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
                    ),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Sam",
                            "Sam19",
                            "GET those quads working and stretching and drop it down low"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            R.raw.video_highleg,
                            "Sam",
                            "sam1",
                            "Heres how to do a sexy highleg"
                    ),
                    FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Ross",
                            "Ross1923",
                            "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Rowland",
                            "rowland.19",
                            "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Steve",
                            "Steve12",
                            "Like the gains 24/7 my friends. You gotta do some stretches"
                    ),
                    FeedItem(
                            R.drawable.avatar_baby,
                            "Bob",
                            "Bob7",
                            "MAXIMUM WEIGHT BOIS AND GALS, MAXIMUM WEIGHT"
                    ),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Sam",
                            "Sam19",
                            "GET those quads working and stretching and drop it down low"
                    ),
                    FeedItem(R.drawable.avatar_baby, "Harry", "Harry1", "Did someone say SPIN class?"),
                    FeedItem(
                            R.drawable.avatar_man,
                            "Ross",
                            "Ross1923",
                            "We all know a Ross is a chad. Just do what I do, and then you can get the MAD gainzz"
                    ),
                    FeedItem(
                            R.drawable.avatar_android,
                            "Rowland",
                            "rowland.19",
                            "They see me rowlin', they hatin', patrollin' and tryna catch me ridin' dirty"
                    )
            )

            //Basic temporary feed generation
            return if(name == null) fullList else fullList.filter { it.name == name }
        }
    }
}