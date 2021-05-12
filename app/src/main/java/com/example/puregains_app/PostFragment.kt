package com.example.puregains_app

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.io.File
import kotlin.Exception


class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_post, container, false)

        val button_get : Button = view.findViewById<Button>(R.id.image_get)
        button_get.setOnClickListener { selectImage(it) }

        val submit_post : Button = view.findViewById<Button>(R.id.post_button_submit)
        submit_post.setOnClickListener {
            createPost(it)
        }

        return view
    }

    fun selectImage(v: View) {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 0)
    }

    fun createPost(view : View) {
        val message : String = requireView().findViewById<TextView>(R.id.post_message_text).text.toString()
        val media : String = requireView().findViewById<TextView>(R.id.image_path_text).text.toString()
        val is_video : Boolean = requireView().findViewById<Switch>(R.id.new_post_video).isChecked
        val post_tags : String = requireView().findViewById<TextView>(R.id.post_tags).text.toString()

        if (post_tags.split(",").size > 3) { // Check number of tags
            errorMessage("Invalid number of tags")
            return
        }

        try {
            Log.i("USERNAME", "Feed video CREATED " + is_video.toString())
            Connect.sendPost(
                message,
                post_tags,
                media,
                is_video,
                requireActivity()
            )
        } catch ( e : Exception) {
            errorMessage("Unable to create post")
        }

        requireActivity().findNavController(view.id).navigate(R.id.action_post_fragment_to_feed_fragment)
    }


    fun errorMessage(message : String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}