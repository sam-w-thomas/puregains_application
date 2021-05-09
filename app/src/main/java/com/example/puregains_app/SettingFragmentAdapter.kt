package com.example.puregains_app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SettingFragmentAdapter(fm: FragmentManager,
                             private val totalTabs: Int) : FragmentPagerAdapter(fm, totalTabs) {

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> SecurityFragment()
            2 -> SoundFragment()
            else -> AccountFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "Security"
            2 -> "Sound"
            else -> "Account"
        }
    }
}