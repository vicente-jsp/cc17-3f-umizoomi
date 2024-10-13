package com.example.cc17mobileapplicationproject

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)

        val searchText = findViewById<SearchView>(R.id.search)
        val searchEditText = searchText.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val

// Change the text color
        searchEditText.setTextColor(Color.BLACK)

// Change the hint color
        searchEditText.setHintTextColor(Color.GRAY)

// Optionally, change the background of the SearchView's EditText
        searchEditText.setBackgroundColor(Color.TRANSPARENT)

        val searchIcon = searchText.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setImageResource(R.drawable.searchtop)
        val closeIcon = searchText.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeIcon.setImageResource(R.drawable.x)

// Change icon color
        searchIcon.setColorFilter(Color.GREEN)
        closeIcon.setColorFilter(Color.GRAY)

        val toolbar: Toolbar = findViewById(R.id.toptoolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val profile: ImageButton = findViewById(R.id.profileicon)
        profile.setOnClickListener {
            // Handle left icon click
        }


        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change in search bar
                return false
            }
        })

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, Dashboard())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    loadFragment(Dashboard())
                    true
                }
                R.id.calendar -> {
                    loadFragment(calendar())
                    true
                }
                R.id.createMeal -> {
                    loadFragment(createmeal())
                    true
                }
                R.id.notifs -> {
                    loadFragment(notifications())
                    true
                }
                R.id.meds -> {
                    loadFragment(meds())
                    true
                }
                else -> false
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle right icon click
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}