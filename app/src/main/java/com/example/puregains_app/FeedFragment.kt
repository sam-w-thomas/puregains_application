package com.example.puregains_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.Exception

class FeedFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_feed, container, false)

        var feedItems : MutableList<FeedItem> = mutableListOf<FeedItem>()
        runBlocking(Dispatchers.IO) {
            val posts = Connect.getPosts(null,null,null)
            posts.forEach {
                val item = FeedItem.generateItem(it, resources, requireActivity().packageName)
                try {
                    feedItems.add(item)
                } catch ( e : Exception) {
                }
            }
        }

        //Manage feed recycler view
        val recyclerView : RecyclerView = view.findViewById<RecyclerView>(R.id.feed_recycler_view)
        recyclerView.adapter = FeedItemAdapter(requireActivity(), requireContext(), feedItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        return view
    }
}