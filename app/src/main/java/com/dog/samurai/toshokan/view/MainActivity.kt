package com.dog.samurai.toshokan.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dog.samurai.toshokan.R

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
