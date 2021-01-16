package com.joaocouto.mdbpopularmovies.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.joaocouto.mdbpopularmovies.R
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var navController: NavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(this, R.id.fHome)

        initListener()
    }

    private fun initListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.moviesFragment -> {
                    mtHome.navigationIcon = null
                    mtHome.title = getString(R.string.app_name)
                }
                R.id.movieFragment -> {
                    mtHome.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                    mtHome.title = ""
                }
            }
        }

        mtHome.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

    fun setActionBarTitle(title: String?) {
        mtHome.title = title
    }
}