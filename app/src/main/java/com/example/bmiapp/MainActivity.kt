package com.example.bmiapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.format.DateFormat
import android.util.Log
import com.example.dao.ItemsDaoImpl
import com.example.entity.ItemsOfBMI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_record.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainContainerFragment = MainFragment()
        val fragmentManager = this.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, mainContainerFragment)
            .addToBackStack(null)
            .commit()


        //フラグメント処理の呼び出し
        val mainFragment = mainFragment as? HeaderFragment
        mainFragment?.setHeader("入力")

        val recordFragment = recordFragment as? HeaderFragment
        recordFragment?.setHeader("履歴")

        listButton.setOnClickListener {
            val listFragment = RecordFragment()
            val fragmentManager = this.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, listFragment)
                .addToBackStack(null)
                .commit()
        }



    }




}









