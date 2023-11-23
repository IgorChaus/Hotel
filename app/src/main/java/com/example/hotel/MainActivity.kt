package com.example.hotel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotel.view.HotelScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            launchMainScreen()
        }
    }

    private fun launchMainScreen(){
        supportFragmentManager.beginTransaction()
            .add(R.id.container_activity, HotelScreen.getInstance())
            .commit()
    }
}