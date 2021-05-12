package com.example.puregains_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class SettingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val tabLayout : TabLayout = view.findViewById(R.id.setting_tab_layout)
        val pageView : ViewPager = view.findViewById(R.id.setting_pager)

        val settingAdapter : FragmentPagerAdapter = SettingFragmentAdapter(childFragmentManager,tabLayout.tabCount)
        pageView.adapter = settingAdapter
        tabLayout.setupWithViewPager(pageView)

        return view
    }
}