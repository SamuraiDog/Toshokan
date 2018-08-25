package com.dog.samurai.toshokan

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchFragment = SearchFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, searchFragment)
        transaction.commit()
    }
}
