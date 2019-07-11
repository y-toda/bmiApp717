package com.example.bmiapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dao.ItemsDaoImpl
import com.example.entity.ItemsOfBMI
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.headerFragment
import java.lang.reflect.Type
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        //フラグメント処理の呼び出し
        val fragment = headerFragment as? HeaderFragment
        fragment?.setHeader("入力")

        //ボタンの処理
        //BMIの計算
        bmiCal.setOnClickListener {
            var strHeight = height.text.toString()
            var strWeight = weight.text.toString()

            //今日の日付を呼び出す
            var date: Date = Date()
            val id: String = DateFormat.format("yyyyMMdd", date).toString()

            //アラートダイアログ:身長/体重が300を超えたらダイアログを出す
            if (strHeight.isEmpty() || strWeight.isEmpty() || 300 <= strHeight.toFloat() || 300 <= strWeight.toFloat()) {
                val TAG = "myMassege"
                var dialog = ConfirmDialog()
                dialog.title = "エラー"
                dialog.msg = "入力値が不適切です。"
                dialog.okText = "再入力する"
                dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
                    Log.d(TAG, "ok clicked")
                }
                dialog.show(supportFragmentManager, "tag")
            } else {

//                val date = SimpleDateFormat("yyyy/MM/dd").format(Date())
                var bmi = calculateLogic(strHeight, strWeight)

                val resultMessage = "あなたのBMIは %.1f でした"
                bmiResult.setText(resultMessage.format(bmi))


                //結果の登録
                bmi?.let { it1 -> saveData(id, strHeight, strWeight, it1) }

//                //TODO
//                val json = "{ \"height\": \"$strHeight\", \"weight\": \"$strWeight\", \"bmi\": \"$bmi\"}"
//                // mapperオブジェクトを作成
//                val mapper = jacksonObjectMapper()
//
//                // jsonをdeserialize
//                // 下の場合はjsonがColor型のオブジェクトにマッピングされる
//                val color = mapper.readValue<String>(json)

//
//                val pref = PreferenceManager.getDefaultSharedPreferences(this)
//                val height = pref.getString("HEIGHT", "")
//                val weight = pref.getString("WEIGHT", "")
//                val saveBmi = pref.getFloat("BMI", 0F)
//
////                content.setText("$height , $weight , $saveBmi")


            }
        }

        //削除ボタン
        deleteButton.setOnClickListener {
//            //今日の日付を呼び出す
//            var date: Date = Date()
//            val id: String = DateFormat.format("yyyy/MM/dd", date).toString()
//
//            val pref = PreferenceManager.getDefaultSharedPreferences(this)
//            //削除する
//            var itemsImpl = ItemsDaoImpl(pref)
//            //saveメソッドの呼び出し
//            itemsImpl.delete(id)

        }


        //保存ボタン
        saveButton.setOnClickListener {
            //bmiの値を取得
            var strHeight = height.text.toString()
            var strWeight = weight.text.toString()
            var bmi = calculateLogic(strHeight, strWeight)

            //今日の日付を呼び出す
            var date: Date = Date()
            val id: String = DateFormat.format("yyyyMMdd", date).toString()

            val pref = PreferenceManager.getDefaultSharedPreferences(this)

            //アラートダイアログ:BMIが未入力のとき
            if (bmi == null) {
                val TAG = "myMassege"
                var dialog = ConfirmDialog()
                dialog.title = "エラー"
                dialog.msg = "BMIが未入力です。"
                dialog.okText = "再入力する"
                dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
                    Log.d(TAG, "ok clicked")
                }
                dialog.show(supportFragmentManager, "tag")
            } else {
                //保存する
                var itemsImpl = ItemsDaoImpl(pref)

                //saveメソッドの呼び出し
                var saveResult = itemsImpl.save(ItemsOfBMI(id, strHeight, strWeight, bmi.toString()))

                if(!saveResult){

                    //updateメソッドの呼び出し
                    itemsImpl.update(id, ItemsOfBMI(id, strHeight, strWeight, bmi.toString()))
                }

                //applyするためのメソッド呼び出し
                itemsImpl.flush()
//
//                //findAll呼び出し
//                var data = itemsImpl.findAll()
//
//                data.forEach{
//                    var id: String = id
//                    var height = strHeight
//                    var weight: String = strWeight
//                    var bmi: String = bmiResult.toString()
//
//
//                    //コメント欄に出力
////                    comment?.setText("$id" , TextView.BufferType.NORMAL)
//                    comment?.setText("$height" , TextView.BufferType.NORMAL)
////                    comment?.setText("$weight" , TextView.BufferType.NORMAL)
////                    comment?.setText("$bmi" , TextView.BufferType.NORMAL)
//                }
            }
        }


    }

    //共有プリファレンス
    @SuppressLint("NewApi")
    private fun saveData(id: String, strHeight: String, strWeight: String, bmi: Float) {

        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        val editor = pref.edit()
        editor.putString("DATE", id)
            .putString("HEIGHT", strHeight)
            .putString("WEIGHT", strWeight)
            .putString("BMI", bmi.toString())
            .apply()

    }

    //bmiを計算する
    fun calculateLogic(strHeight: String, strWeight: String): Float? {

        val height: Float? = strHeight.toFloatOrNull()?.div(100)
        val weight: Float? = strWeight.toFloatOrNull()

        var bmi = ((height?.times(height))?.let { weight?.div(it) })

        return bmi
    }

//         // ダイアログメソッド
//    fun dialog(msg: String, okText: String){
//        val TAG = "myMessage"
//        deleteButton.setOnClickListener {
//            var dialog = ConfirmDialog()
//            dialog.title = "エラー"
//            dialog.msg = msg
//            dialog.okText = okText
//            dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
//                Log.d(TAG, "ok clicked")
//            }
//
//            dialog.show(supportFragmentManager, "tag")
//        }
//    }





}

