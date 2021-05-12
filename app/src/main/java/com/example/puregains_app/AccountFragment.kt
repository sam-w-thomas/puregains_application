package com.example.puregains_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar


class AccountFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val account_button = view.findViewById<Button>(R.id.account_update_button)

        account_button.setOnClickListener {
            val new_tags : String = view.findViewById<TextView>(R.id.update_account_tags).text.toString()
            val new_avatar : String = view.findViewById<TextView>(R.id.update_account_avatar).text.toString()
            val new_desc : String = view.findViewById<TextView>(R.id.update_account_bio).text.toString()
            val new_name : String = view.findViewById<TextView>(R.id.update_account_name).text.toString()

            if (!Connect.updateUser(
                new_name,
                new_desc,
                new_tags,
                new_avatar,
                Auth.getUsername(requireActivity()),
                Auth.getToken(requireActivity())
                )
            ) {
                val snackbar = Snackbar.make(requireView(), "Unable to update user", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }

        return view
    }
}