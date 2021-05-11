package com.example.puregains_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationTargetException


class ProfileFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val args : ProfileFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_profile, container, false)
        var username : String
        try {
            username = args.username
        } catch ( e : InvocationTargetException) {
            username = Auth.getUsername(requireActivity())
        }

        view.findViewById<TextView>(R.id.profile_username).text = username

        // get user profile
        runBlocking(Dispatchers.IO) {
            val user_info = Connect.getUser(username)
            updateProfile(
                    view,
                    user_info.get("name").toString(),
                    user_info.get("description").toString(),
                    user_info.get("user_tags").toString(),
                    requireActivity().resources.getIdentifier(user_info.get("avatar_path").toString() , "raw", requireActivity().packageName)
            )
        }

        // create feed
        val feedItems : MutableList<FeedItem> = mutableListOf<FeedItem>()
        runBlocking(Dispatchers.IO) {
            val posts = Connect.getPosts(username,null,null)
            posts.forEach {
                val item = FeedItem.generateItem(it, resources, requireActivity().packageName)
                try {
                    feedItems.add(item)
                } catch ( e : Exception) {
                }
            }
        }

        val recyclerView : RecyclerView = view.findViewById<RecyclerView>(R.id.profile_feed_view)
        recyclerView.adapter = FeedItemAdapter(requireActivity(), requireContext(), feedItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        return view
    }

    fun updateProfile(
            view : View,
            name : String,
            desc : String,
            user_tags : String,
            avatar_path : Int,
    ) {
        val name_view : TextView = view.findViewById<TextView>(R.id.profile_name)
        name_view.text = name

        val desc_view : TextView = view.findViewById<TextView>(R.id.profile_desc)
        desc_view.text = desc

        val avatar_picture : ImageView = view.findViewById<ImageView>(R.id.avatar_image)
        avatar_picture.setImageResource(avatar_path)

        // update tags
        val tag_layout = view.findViewById<LinearLayout>(R.id.profile_tags)
        for (tag in user_tags.split(",")) {
            FeedItemAdapter.addTags(
                    tag_layout,
                    tag
            )
        }
    }
}