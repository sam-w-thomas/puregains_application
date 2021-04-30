package com.example.puregains_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

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
        try {
            val intent : Intent = Intent(requireActivity(), CoreActivity::class.java)
            requireActivity().startActivity(intent)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}