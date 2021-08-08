package com.github.diabetesassistant

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.diabetesassistant.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = getNavController()
        val loggedInMenuIds = setOf(R.id.nav_dashboard, R.id.doctorManagement)
        val menuIds = setOf(R.id.loginActivity, R.id.nav_dashboard)
        val sharedPref = getSharedPreferences(getString(R.string.app_prefix), Context.MODE_PRIVATE)
        val accessKey = sharedPref.getString(getString(R.string.access_token_key), "")

        if (accessKey!!.isNotEmpty()) {
            Log.i("main", "logged in")
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.activity_logged_in_drawer)
            val email = sharedPref.getString(getString(R.string.email), "")
            val headerView: View = binding.navView.getHeaderView(0)
            val headerMainText = headerView.findViewById(R.id.nav_header_main_text) as TextView
            headerMainText.text = email
            val headerSubText = headerView.findViewById(R.id.nav_header_sub_text) as TextView
            headerSubText.text = getString(R.string.nav_header_title_logged_in)
            appBarConfiguration = AppBarConfiguration(loggedInMenuIds, drawerLayout)
        } else {
            Log.i("main", "not logged in")
            appBarConfiguration = AppBarConfiguration(menuIds, drawerLayout)
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun getNavController(): NavController {
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        check(fragment is NavHostFragment) {
            ("Activity $this does not have a NavHostFragment")
        }
        return (fragment as NavHostFragment?)!!.navController
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
