package com.example.bmiapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.inputButton -> {
                textMessage.setText(R.string.inputButton)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, MainFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.listButton -> {
                textMessage.setText(R.string.listButton)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, RecordFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }


        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val mainContainerFragment = MainFragment()
        val fragmentManager = this.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, mainContainerFragment)
            .addToBackStack(null)
            .commit()


        //フラグメント処理の呼び出し
        val mainFragment = headerFragment as? HeaderFragment
        mainFragment?.setHeader("入力")
    }
}
