package com.example.hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            launchScreen()
        }
    }


    private fun launchScreen(){
        supportFragmentManager.beginTransaction().add(R.id.container_activity, RoomScreen.getInstance())
            .commit()
    }
}