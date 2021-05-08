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
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import java.io.File
import kotlin.Exception


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PostFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_post, container, false)

        val button_get : Button = view.findViewById<Button>(R.id.image_get)
        button_get.setOnClickListener { selectImage(it) }

        val submit_post : Button = view.findViewById<Button>(R.id.post_button_submit)
        submit_post.setOnClickListener {
            createPost()
        }

        return view
    }

    /**
     * Handle getting correct connection string
     * Initiate server connect
     */

    fun selectImage(v: View) {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 0)
    }

    fun createPost() {
        val message : String = requireView().findViewById<TextView>(R.id.post_message_text).text.toString()
        val media : String = requireView().findViewById<TextView>(R.id.image_path_text).text.toString()
        val is_video : Boolean = requireView().findViewById<Switch>(R.id.new_post_video).isActivated
        val post_tags : String = requireView().findViewById<TextView>(R.id.post_tags).text.toString()

        try {
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

    }

    fun errorMessage(message : String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}