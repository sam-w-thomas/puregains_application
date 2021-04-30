package com.example.puregains_app

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class LoginFragmentAdapter(fm: FragmentManager,
                           private val totalTabs: Int) : FragmentPagerAdapter(fm, totalTabs) {

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            1 -> RegisterFragment()
            else -> LoginFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            1 -> "Register"
            else -> "Login"
        }
    }
}