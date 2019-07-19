package com.example.bmiapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


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
        val mainFragment = headerFragment as? HeaderFragment
        mainFragment?.setHeader("入力")



        listButton.setOnClickListener {

            val listFragment = RecordFragment()
            val fragmentManager = this.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, listFragment)
                .addToBackStack(null)
                .commit()

            //フラグメント処理の呼び出し
            val recordFragment = headerFragment as? HeaderFragment
            recordFragment?.setHeader("履歴")
        }

        inputButton.setOnClickListener {
            //フラグメント処理の呼び出し
            val mainFragment = headerFragment as? HeaderFragment
            mainFragment?.setHeader("入力")

            val mainContainerFragment = MainFragment()
            val fragmentManager = this.getSupportFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, mainContainerFragment)
                .addToBackStack(null)
                .commit()
        }

    }

}









