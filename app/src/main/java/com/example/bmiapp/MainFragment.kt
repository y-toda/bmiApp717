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
import androidx.appcompat.app.AlertDialog
import com.example.dao.ItemsDaoImpl
import com.example.entity.ItemsOfBMI
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */

class MainFragment : Fragment(), onClickListener {

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

            //TODO 正規表現したい
            val regex = Regex("""\d{1,3}(\.\d{1})?""")
            val heightRule = regex.matches("${strHeight}")
            val weightRule = regex.matches("${strWeight}")

            //アラートダイアログ:身長/体重が300を超えたらダイアログを出す
            if ( !heightRule || !weightRule || strHeight.isEmpty() || strWeight.isEmpty() || 300 <= strHeight.toFloat() || 300 <= strWeight.toFloat()) {

                activity?.let { it1 ->
                    AlertDialog.Builder(it1)
                        .setTitle("エラー")
                        .setMessage("入力値が不適切です")
                        .setPositiveButton("再入力する", null)
                        .show()
                }

            } else {

                var bmi = calculateLogic(strHeight, strWeight)
                //コメントが登録されていたらコメントも一緒に登録する
                var contents: String? = contents.text.toString()

                val pref = PreferenceManager.getDefaultSharedPreferences(activity)
                //保存する
                var itemsImpl = ItemsDaoImpl(pref)
                //saveメソッドの呼び出し
                var saveResult = itemsImpl.save(ItemsOfBMI(id, strHeight, strWeight, bmi.toString(), contents))

                if (!saveResult) {
                    //updateメソッドの呼び出し
                    itemsImpl.update(id, ItemsOfBMI(id, strHeight, strWeight, bmi.toString(), contents))
                }
                //applyするためのメソッド呼び出し
                itemsImpl.flush()


                // BMIメッセージの表示
                val resultMessage = "あなたのBMIは %.1f でした"
                bmiResult.setText(resultMessage.format(bmi))


//                //結果の登録
//                bmi?.let { it1 -> saveData(id, strHeight, strWeight, it1) }

            }
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
        //保存ボタン押下時
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
                Log.d("bmiAlart", "BMIの値がぬるなんだよ！！")
                activity?.let { it1 ->
                    AlertDialog.Builder(it1)
                        .setTitle("エラー")
                        .setMessage("BMIが未入力です")
                        .setPositiveButton("再入力する", null)
                        .show()
                }


            } else {
                //保存する
                var itemsImpl = ItemsDaoImpl(pref)

                //saveメソッドの呼び出し
                var saveResult = itemsImpl.save(ItemsOfBMI(id, strHeight, strWeight, bmi.toString(), contents))

                if (!saveResult) {

                    //updateメソッドの呼び出し
                    itemsImpl.update(id, ItemsOfBMI(id, strHeight, strWeight, bmi.toString(), contents))
                }

                //applyするためのメソッド呼び出し
                itemsImpl.flush()

            }
        }

        return view
    }

    //bmiを計算する
    fun calculateLogic(strHeight: String?, strWeight: String?): Float? {

        val height: Float? = strHeight?.toFloatOrNull()?.div(100)
        val weight: Float? = strWeight?.toFloatOrNull()

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
