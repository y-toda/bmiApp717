package com.example.bmiapp

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dao.ItemsDaoImpl
import com.example.entity.ItemsOfBMI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = recycle_id
        val adapter = ViewAdapter(createDataList())

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun createDataList(): List<RowModel> {

        //登録されたデータの取得
        val pref = PreferenceManager.getDefaultSharedPreferences(activity)
        val dao = ItemsDaoImpl(pref)
        val itemsList = dao.findAll().sortedBy { it.id }

        val dataList = mutableListOf<RowModel>()

        //月初めを判断する変数の宣言
        var month: String? = null

        itemsList.forEach {

            val data = it

            //変数monthと月が一致しなかったら月初めのデータである
            if (data.id.substring(4,6) != month) {
                Log.d("RecordFragment", "セクションを表示します")
                val section = RowModel(RecyclerType.SECTION, data)
                dataList.add(section)
            }
            //変数monthに今回の月を代入
            month = data.id.substring(4,6)

            // BODYをセット
            Log.d("RecordFragment", "BODYを表示します")
            val body = RowModel(RecyclerType.BODY, data)
            dataList.add(body)



            // メモが登録された
            if(!data.contents.isNullOrEmpty()){
                val detail = RowModel(RecyclerType.DETAIL, data)
                dataList.add(detail)
            }

        }
        return dataList
    }
}
