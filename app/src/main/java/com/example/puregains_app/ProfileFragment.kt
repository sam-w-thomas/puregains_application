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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [profileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<TextView>(R.id.profile_username).text = Auth.getUsername(requireActivity())

        // get user profile
        runBlocking(Dispatchers.IO) {
            val user_info = Connect.getUser(Auth.getUsername(requireActivity()))
            updateProfile(
                    view,
                    user_info.get("name").toString(),
                    user_info.get("description").toString(),
                    user_info.get("user_tags").toString(),
                    requireActivity().resources.getIdentifier(user_info.get("avatar_path").toString() , "raw", requireActivity().packageName)
            )

            Log.i("POSTS",user_info.toString())
        }

        // create feed
        val feedItems : MutableList<FeedItem> = mutableListOf<FeedItem>()
        runBlocking(Dispatchers.IO) {
            val posts = Connect.getPosts(Auth.getUsername(requireActivity()),null,null)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment profileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}