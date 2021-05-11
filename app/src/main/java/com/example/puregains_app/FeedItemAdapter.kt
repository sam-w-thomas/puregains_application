package com.example.puregains_app

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.math.floor

const val ADVERT_FACTOR : Int = 4

class FeedItemAdapter(
    val activity : Activity,
        val context : Context,
        val items: List<FeedItem>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //Inflate three types of cards
        return when(viewType) {
            TYPE_ADVERT_BANNER -> FeedAdvertBanner(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.advert_banner,
                    parent,
                    false
                )
            )
            TYPE_ADVERT_LARGER -> FeedAdvertLarger(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.advert_larger,
                    parent,
                    false
                )
            )
            TYPE_VIDEO -> FeedVideoHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.feed_video_item,
                            parent,
                            false
                    )
            )
            TYPE_PHOTO -> FeedPhotoHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.feed_photo_item,
                    parent,
                    false
                )
            )
            else -> FeedItemHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.feed_item,
                    parent,
                    false)
            )
        }
    }

    /**
     * Video automatically playing when scrolling
     */
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)

        if(holder is FeedVideoHolder) {
            holder.video.start()
        }
    }

    /**
     * Video automatically pause when scrolling
     */
    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        if(holder is FeedVideoHolder) {
            holder.video.pause()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position - (position/ADVERT_FACTOR)]

        if (holder is FeedItemHolder) {
            holder.image.setImageResource(currentItem.image)
            holder.name.text = currentItem.name
            holder.username.text = currentItem.username
            holder.message.text = currentItem.message
            holder.likes.text = currentItem.likes.toString()
            holder.like_thumb.setOnClickListener {
                runBlocking(Dispatchers.IO) {
                    Connect.updateLikes(
                        currentItem.post_id,
                        activity
                    )
                    //holder.likes.text = (holder.likes.text.toString().toInt() + 1).toString()
                }
                holder.likes.text = (holder.likes.text.toString().toInt() + 1).toString()
            }
            holder.image.setOnClickListener {
                val action = FeedFragmentDirections.actionFeedFragmentToProfileFragment(currentItem.username)
                activity.findNavController(R.id.nav_host_fragment).navigate(action)
            }
            for (tag in currentItem.tags.split(",")) {
                addTags(
                    holder.tag_layout,
                    tag
                )
            }

        } else if (holder is FeedVideoHolder) {
            Log.i("USERNAME", "Feed video holder created")
            holder.image.setImageResource(currentItem.image)
            holder.name.text = currentItem.name
            holder.username.text = currentItem.username
            holder.message.text = currentItem.message
            holder.likes.text = currentItem.likes.toString()
            holder.like_thumb.setOnClickListener {
                runBlocking(Dispatchers.IO) {
                    Connect.updateLikes(
                        currentItem.post_id,
                        activity
                    )
                }
                holder.likes.text = (holder.likes.text.toString().toInt() + 1).toString()
            }
            holder.image.setOnClickListener {
                val action = FeedFragmentDirections.actionFeedFragmentToProfileFragment(currentItem.username)
                activity.findNavController(R.id.nav_host_fragment).navigate(action)
            }

            for (tag in currentItem.tags.split(",")) {
                addTags(
                    holder.tag_layout,
                    tag
                )
            }


            val uri: Uri = Uri.parse("android.resource://" +  context.packageName + "/" + currentItem.video)
            holder.video.setVideoURI(uri)
            holder.video.setZOrderOnTop(false)

            //Set listeners to pause / play
            holder.video.setOnClickListener {
                val videoView : VideoView = holder.video
                if(videoView.isPlaying) {
                    videoView.pause()
                } else {
                    videoView.start()
                }
            }

        } else if (holder is FeedPhotoHolder) {
            Log.i("USERNAME", "Feed photo holder created")
            holder.image.setImageResource(currentItem.image)
            holder.name.text = currentItem.name
            holder.username.text = currentItem.username
            holder.message.text = currentItem.message
            holder.photo.setImageResource(currentItem.photo)
            holder.likes.text = currentItem.likes.toString()
            holder.like_thumb.setOnClickListener {
                runBlocking(Dispatchers.IO) {
                    Connect.updateLikes(
                        currentItem.post_id,
                        activity
                    )
                }
                holder.likes.text = (holder.likes.text.toString().toInt() + 1).toString()
            }
            holder.image.setOnClickListener {
                val action = FeedFragmentDirections.actionFeedFragmentToProfileFragment(currentItem.username)
                activity.findNavController(R.id.nav_host_fragment).navigate(action)
            }
            for (tag in currentItem.tags.split(",")) {
                addTags(
                    holder.tag_layout,
                    tag
                )
            }
        } else if (holder is FeedAdvertBanner) {
            return
        } else if (holder is FeedAdvertLarger) {
            return
        }
    }

    override fun getItemCount(): Int {
        val count = items.size + (items.size/(ADVERT_FACTOR))
        Log.i("USERNAME", count.toString())
        return count
    }

    override fun getItemViewType(position: Int): Int {
        val return_type : Int

        if (position == 0) return items[position].viewType

        if (position.rem((ADVERT_FACTOR * 2) + 2) == 0) {
            return_type = TYPE_ADVERT_LARGER
        } else if (position.rem(ADVERT_FACTOR + 1) == 0) {
            return_type = TYPE_ADVERT_BANNER
        } else {
            val adjusted_position = position - (position/(ADVERT_FACTOR))
            return_type = items[adjusted_position].viewType
        }
        return return_type
    }

    class FeedItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.feed_item_avatar)
        val name : TextView = item.findViewById(R.id.feed_item_name)
        val username : TextView = item.findViewById(R.id.feed_item_username)
        val message : TextView = item.findViewById(R.id.feed_item_message)
        val tag_layout : LinearLayout = item.findViewById(R.id.profile_tags)
        val like_thumb : ImageView = item.findViewById(R.id.feed_thumbs_up)
        val likes : TextView = item.findViewById(R.id.feed_likes)
    }

    class FeedVideoHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.feed_video_item_avatar)
        val name : TextView = item.findViewById(R.id.feed_video_item_name)
        val username : TextView = item.findViewById(R.id.feed_video_item_username)
        val message : TextView = item.findViewById(R.id.feed_video_item_message)
        val video : VideoView = item.findViewById(R.id.feed_video)
        val tag_layout : LinearLayout = item.findViewById(R.id.profile_tags)
        val like_thumb : ImageView = item.findViewById(R.id.feed_thumbs_up)
        val likes : TextView = item.findViewById(R.id.feed_likes)
    }

    class FeedPhotoHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.feed_photo_item_avatar)
        val name : TextView = item.findViewById(R.id.feed_photo_item_name)
        val username : TextView = item.findViewById(R.id.feed_photo_item_username)
        val message : TextView = item.findViewById(R.id.feed_photo_item_message)
        val photo : ImageView = item.findViewById(R.id.feed_photo)
        val tag_layout : LinearLayout = item.findViewById(R.id.profile_tags)
        val like_thumb : ImageView = item.findViewById(R.id.feed_thumbs_up)
        val likes : TextView = item.findViewById(R.id.feed_likes)
    }

    class FeedAdvertBanner(item: View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.advert_banner_image)
    }

    class FeedAdvertLarger(item: View) : RecyclerView.ViewHolder(item) {
        val image: ImageView = item.findViewById(R.id.advert_large_image)
    }

    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_VIDEO = 1
        const val TYPE_PHOTO = 2
        const val TYPE_ADVERT_BANNER = 10
        const val TYPE_ADVERT_LARGER = 11

        fun addTags(
                layout : LinearLayout,
                tag : String
        ) {
            if (tag == "") { // cancel blank tags
                return
            }

            val tagButton = Button(layout.context)

            val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    60,
            )
            layoutParams.setMargins(15)

            tagButton.layoutParams = layoutParams
            tagButton.setPadding(0)
            tagButton.text = tag
            tagButton.setTextColor(Color.WHITE)
            tagButton.setBackgroundColor(Color.parseColor("#516470"))
            tagButton.alpha = 0.5F

            layout.addView(tagButton)
        }
    }
}