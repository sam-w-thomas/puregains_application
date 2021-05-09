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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AccountFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}