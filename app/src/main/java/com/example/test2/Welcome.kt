package com.example.test2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Welcome : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var showhello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        sp = getSharedPreferences("username", MODE_PRIVATE)
        showhello = findViewById(R.id.mainword)

        showhello.text = "歡迎！${sp.getString("Loginname", "")}"
    }
}
