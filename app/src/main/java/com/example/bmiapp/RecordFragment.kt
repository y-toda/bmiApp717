package com.example.bmiapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : Fragment()  {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("lifeCycle", "onViewCreated")

        val recyclerView = recycler_list
        val adapter = ViewAdapter(createDataList(), object : ViewAdapter.ListListener {
            override fun onClickRow(tappedView: View, rowModel: RowModel) {
                this@RecordFragment.onClickRow(tappedView, rowModel)
            }
        })

//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = adapter
    }

    private fun createDataList(): List<RowModel> {

        val dataList = mutableListOf<RowModel>()
        for (i in 0..49) {
            val data: RowModel = RowModel().also {
                it.date = "タイトル" + i + "だよ"
                it.height = "詳細" + i + "個目だよ"
                it.weight = "weight"
                it.bmi = "bmi"
            }
            dataList.add(data)
        }
        return dataList
    }


    fun onClickRow(tappedView: View, rowModel: RowModel) {
        Snackbar.make(tappedView, "Replace with your own action tapped ${rowModel.date}", Snackbar.LENGTH_LONG)     //dateは元々titleが入ってたヨ
            .setAction("Action", null).show()
    }
}