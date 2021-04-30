package com.example.puregains_app

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class FeedItemAdapter(
        val context : Context,
        val items: List<FeedItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //Inflate two types of cards (Text & Text+Video)
        return when(viewType) {
            0 -> FeedItemHolder(
                    LayoutInflater.from(parent.context).inflate(
                    R.layout.feed_item,
                    parent,
                    false)
            )
            else -> FeedVideoHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.feed_video_item,
                            parent,
                            false
                    )
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
        val currentItem = items[position]

        if (holder is FeedItemHolder) {
            holder.image.setImageResource(currentItem.image)
            holder.name.text = currentItem.name
            holder.username.text = currentItem.username
            holder.message.text = currentItem.message
        } else if (holder is FeedVideoHolder) {
            holder.image.setImageResource(currentItem.image)
            holder.name.text = currentItem.name
            holder.username.text = currentItem.username
            holder.message.text = currentItem.message

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

            //Configure video controller (ie controls playing)
            //val controller : MediaController = MediaController(context)
            //controller.setAnchorView(holder.video)
            //holder.video.setMediaController(controller)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    class FeedItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.feed_item_avatar)
        val name : TextView = item.findViewById(R.id.feed_item_name)
        val username : TextView = item.findViewById(R.id.feed_item_username)
        val message : TextView = item.findViewById(R.id.feed_item_message)
    }

    class FeedVideoHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image : ImageView = item.findViewById(R.id.feed_video_item_avatar)
        val name : TextView = item.findViewById(R.id.feed_video_item_name)
        val username : TextView = item.findViewById(R.id.feed_video_item_username)
        val message : TextView = item.findViewById(R.id.feed_video_item_message)
        val video : VideoView = item.findViewById(R.id.feed_video)
    }

    companion object {
        const val TYPE_ONE = 0
        const val TYPE_TWO = 1
    }
}