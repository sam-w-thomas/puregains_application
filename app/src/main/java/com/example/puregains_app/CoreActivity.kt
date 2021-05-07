package com.example.puregains_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class CoreActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.core_activity)

        val bottomMenu = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        val navController = findNavController(R.id.nav_host_fragment)
        val title : TextView = findViewById<TextView>(R.id.title_text)
        val logout_button : Button = findViewById<Button>(R.id.logout_button)

        logout_button.setOnClickListener {
            userLogout()
        }

        bottomMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            title.text = destination.label
        }

    }

    fun userLogout() {
        Auth.clearToken(this)
        Auth.clearUser(this)

        try {
            val intent : Intent = Intent(this, LoginActivity::class.java)
            this.startActivity(intent)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }
}