package com.example.bmiapp


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dao.ItemsDaoImpl
import com.example.entity.ItemsOfBMI
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*


//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

class MainFragment : Fragment(), onClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val bmiCal = view.findViewById<View>(R.id.bmiCal)
        bmiCal.setOnClickListener {
            var strHeight = height.text.toString()
            var strWeight = weight.text.toString()

            //今日の日付を呼び出す
            var date = Date()
            val id: String = DateFormat.format("yyyyMMdd", date).toString()

            //アラートダイアログ:身長/体重が300を超えたらダイアログを出す
            if (strHeight.isEmpty() || strWeight.isEmpty() || 300 <= strHeight.toFloat() || 300 <= strWeight.toFloat()) {
//                val TAG = "myMassege"
//                var dialog = ConfirmDialog()
//                dialog.title = "エラー"
//                dialog.msg = "入力値が不適切です。"
//                dialog.okText = "再入力する"
//                dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
//                    Log.d(TAG, "ok clicked")
//                }
//                dialog.show(supportFragmentManager, "tag")
            } else {

//                val date = SimpleDateFormat("yyyy/MM/dd").format(Date())
                var bmi = calculateLogic(strHeight, strWeight)

                val resultMessage = "あなたのBMIは %.1f でした"
                bmiResult.setText(resultMessage.format(bmi))


                //結果の登録
                bmi?.let { it1 -> saveData(id, strHeight, strWeight, it1) }

            }

            val deleteButton = view.findViewById<View>(R.id.deleteButton)
            //削除ボタン
            deleteButton.setOnClickListener {

                Log.d("deleteButton", "ボタンは押せたよ")
                //今日の日付を呼び出す
                var date: Date = Date()
                val id: String = DateFormat.format("yyyyMMdd", date).toString()

                val pref = PreferenceManager.getDefaultSharedPreferences(activity)
                //削除する
                var itemsImpl = ItemsDaoImpl(pref)
                //saveメソッドの呼び出し
                itemsImpl.delete(id)
                itemsImpl.flush()

                Log.d("deleteButton", "削除しました。")

            }

            val saveButton = view.findViewById<View>(R.id.saveButton)
            //保存ボタン
            saveButton.setOnClickListener {
                //bmiの値を取得
                var strHeight = height.text.toString()
                var strWeight = weight.text.toString()
                var bmi = calculateLogic(strHeight, strWeight)
                var contents: String? = contents.text.toString()

                //今日の日付を呼び出す
                var date: Date = Date()
                val id: String = DateFormat.format("yyyyMMdd", date).toString()

                val pref = PreferenceManager.getDefaultSharedPreferences(activity)

                //アラートダイアログ:BMIが未入力のとき
                if (bmi == null) {
//                    val TAG = "myMassege"
//                    var dialog = ConfirmDialog()
//                    dialog.title = "エラー"
//                    dialog.msg = "BMIが未入力です。"
//                    dialog.okText = "再入力する"
//                    dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
//                        Log.d(TAG, "ok clicked")
//                    }
//                    dialog.show(supportFragmentManager, "tag")
                } else {
                    //保存する
                    var itemsImpl = ItemsDaoImpl(pref)

                    //saveメソッドの呼び出し
                    var saveResult = itemsImpl.save(ItemsOfBMI(id, strHeight, strWeight, bmi.toString(), contents))

                    if(!saveResult){

                        //updateメソッドの呼び出し
                        itemsImpl.update(id, ItemsOfBMI(id, strHeight, strWeight, bmi.toString(), contents))
                    }

                    //applyするためのメソッド呼び出し
                    itemsImpl.flush()

                }
            }
        }
        return view
    }
    //bmiを計算する
    fun calculateLogic(strHeight: String, strWeight: String): Float? {

        val height: Float? = strHeight.toFloatOrNull()?.div(100)
        val weight: Float? = strWeight.toFloatOrNull()

        var bmi = ((height?.times(height))?.let { weight?.div(it) })

        return bmi
    }


    //共有プリファレンス
    @SuppressLint("NewApi")
    private fun saveData(id: String, strHeight: String, strWeight: String, bmi: Float) {

        val pref = PreferenceManager.getDefaultSharedPreferences(getActivity())

        val editor = pref.edit()
        editor.putString("DATE", id)
            .putString("HEIGHT", strHeight)
            .putString("WEIGHT", strWeight)
            .putString("BMI", bmi.toString())
            .apply()

    }






}

interface onClickListener {

}
