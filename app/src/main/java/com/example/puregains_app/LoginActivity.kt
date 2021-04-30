package com.example.puregains_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tabLayout : TabLayout = findViewById(R.id.login_register_nav)
        val pageView : ViewPager = findViewById(R.id.login_register_pager)

        tabLayout.addTab(tabLayout.newTab().setText("Login"))
        tabLayout.addTab(tabLayout.newTab().setText("Register"))

        val loginAdapter : FragmentPagerAdapter = LoginFragmentAdapter(supportFragmentManager,tabLayout.tabCount)
        pageView.adapter = loginAdapter
        tabLayout.setupWithViewPager(pageView)
    }
}