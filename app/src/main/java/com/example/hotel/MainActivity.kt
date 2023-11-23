package com.example.hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotel.view.HotelScreen
import com.example.hotel.view.RoomScreen

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