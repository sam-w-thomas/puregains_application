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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        userCheck()

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

    fun userCheck() {
        if (Auth.hasAccount(this) and Auth.hasToken(this)) {
            lauchMain()
        } else if (Auth.hasAccount(this) and !Auth.hasToken(this)) {
            try {
                getToken()
            } catch ( e : Exception) {
                return
            }
        } else {
            if (Auth.hasToken(this)) Auth.clearToken(this) // check for token, if somehow there
            return
        }
    }

    fun lauchMain() {
        try {
            val intent : Intent = Intent(this, CoreActivity::class.java)
            this.startActivity(intent)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun getToken() {
        lateinit var token : String
        runBlocking(Dispatchers.IO) {
            token = Auth.requestToken(this@LoginActivity)
        }

        Auth.setToken(token,this)
    }
}