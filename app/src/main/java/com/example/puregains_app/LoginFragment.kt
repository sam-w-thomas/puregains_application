package com.example.puregains_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment {
    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.login_fragment, container, false)

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener { userLogin() }

        return view
    }

    fun userLogin() {
        val username : String = requireView().findViewById<TextView>(R.id.login_username).text.toString()
        val password : String = requireView().findViewById<TextView>(R.id.login_password).text.toString()

        Auth.setUser(
            username,
            password,
            requireActivity()
        )

        try {
            lateinit var token : String
            runBlocking(Dispatchers.IO) {
                token = Auth.requestToken(requireActivity())
            }

            Auth.setToken(token,requireActivity())

            try {
                val intent : Intent = Intent(requireActivity(), CoreActivity::class.java)
                requireActivity().startActivity(intent)
            } catch (e : Exception) {
                e.printStackTrace()
            }

        } catch (e : Exception) {
            errorMessage("Invalid password")
        }



    }

    fun errorMessage(message : String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}