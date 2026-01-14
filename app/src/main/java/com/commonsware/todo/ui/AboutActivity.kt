package com.commonsware.todo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.commonsware.todo.R
import com.commonsware.todo.ui.roster.RosterListFragmentDirections
import com.commonsware.todo.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.app_name)
        binding.about.loadUrl("file:///android_asset/about.html")

        setSupportActionBar(binding.toolbar)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actions_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId){
        R.id.home -> {
            startActivity(Intent(this, MainActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}