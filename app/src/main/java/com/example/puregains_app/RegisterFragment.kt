package com.example.puregains_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.lang.Exception


class RegisterFragment : Fragment {
    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view : View =  inflater.inflate(R.layout.register_fragment, container, false)

        val image_button : Button = view.findViewById<Button>(R.id.register_image)
        image_button.setOnClickListener { selectImage(it) }

        val signup_button : Button = view.findViewById<Button>(R.id.signup_button)
        signup_button.setOnClickListener {
            signUp()
            userLogin()
        }

        return view
    }

    fun selectImage(v: View) {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 0)
    }

    fun signUp() {
        if (view == null) {
            return
        }

        try {
            // Name
            var name = view?.findViewById<TextView>(R.id.register_name)?.text.toString()
            if ((name == "null") or (name == "")) {
                errorMessage("Invalid name")
                return
            }

            // Birth date
            var birth = view?.findViewById<TextView>(R.id.login_password)?.text.toString()
            val date_regex = Regex("\\d{4}/\\d{2}/\\d{2}")
            if (!date_regex.matches(birth)) {
                errorMessage("Invalid birth date")
                return
            }

            // Avatar path
            var avatar = view?.findViewById<TextView>(R.id.login_username)?.text.toString()
            if ((avatar == "null") or (avatar == "")) {
                errorMessage("Invalid avatar")
                return
            }

            // Password
            var password = view?.findViewById<TextView>(R.id.register_password)?.text.toString()
            if ((password == "null") or (password == "")) {
                errorMessage("Invalid password")
                return
            }

            lifecycleScope.launch(Dispatchers.Main) {
                lateinit var username: String
                lateinit var token: String
                async(Dispatchers.IO) {
                    username = Auth.createUser(
                            name,
                            birth,
                            avatar,
                            password,
                            requireActivity()
                    )
                }.await()

                Auth.setUser(
                        username,
                        password,
                        requireActivity()
                )

                async(Dispatchers.IO) {
                    token = Auth.requestToken(
                            requireActivity()
                    )
                }.await()

                Auth.setToken(
                        token,
                        requireActivity()
                )
            }
        } catch ( e : Exception ) {
            errorMessage(e.toString())
        }
    }

    fun errorMessage(message : String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    fun userLogin() {
        try {
            val intent : Intent = Intent(requireActivity(), CoreActivity::class.java)
            requireActivity().startActivity(intent)
        } catch (e : Exception) {
            errorMessage(e.toString())
        }
    }
 }