package com.example.dao

import android.content.SharedPreferences
import android.util.Log
import com.example.entity.ItemsOfBMI
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

const val KEY_ITEMS_LIST = "KEY_ITEMS_LIST"

class ItemsDaoImpl(sharedPreferences: SharedPreferences) {

    //mutable:状態を変えることができる
    //mutableSetOf で値をセット。
    private var itemsList = mutableSetOf<ItemsOfBMI>()
    private var pref = sharedPreferences

    init {
        //getStringSet　でJSON呼び出し
        val json = pref.getStringSet(KEY_ITEMS_LIST, null)

        json?.let {
            itemsList = jsonSetToObject(json)
        }
    }

    fun save(item: ItemsOfBMI): Boolean{
        // Id(date)が一致するなら同一オブジェクトが存在するため保存しない
        itemsList.forEach {
            if(it?.id == item.id) return false
        }
        // 同一IDが存在しない場合、新規保存
        itemsList.add(item)
        return true
    }

    fun findAll(): MutableSet<ItemsOfBMI> {
        // ここでNULLチェックはなし
        return this.itemsList
    }

    fun findById(id: String): ItemsOfBMI?{
        for (item in itemsList) {
            if (item?.id == id) return item
        }
        // 対象のIDがなければNULLを返却する
        return null
    }

    fun update(id: String, item: ItemsOfBMI): Boolean {
        //
        itemsList.forEach {
            if(it?.id == id) {
                // Setの中にある同一要素を削除する
                itemsList.remove(it)
                // 引数のオブジェクトを追加する
                itemsList.add(item)

                // 更新が完了したら true
                return true
            }
        }
        // 更新対象がなかったら false
        return false
    }

    fun delete(id: String): Boolean {
        itemsList.forEach {
            if(it?.id == id) {
                // 削除対象のインデックスを取得
                itemsList.remove(it)

                // 削除が完了したらtrue
                return true
            }
        }
        return false
    }

    fun flush() {
        val editor = this.pref.edit()
        // 共有プリファレンスに現在の状態を保存する。
        editor.putStringSet(KEY_ITEMS_LIST,convertJsonSet())
            .apply()
    }

    /**
     * SharedPreferenceから取得したMutableSet<String>をオブジェクトとして使えるようにする
     * @param {MutableSet<String>} SharedPreferenceから取得した値
     * @return {MutableSet<ItemsOfBMI?>} jsonなStringをItemsOfBMIに変換した値が返却される
     */
    private fun jsonSetToObject(items: MutableSet<String>): MutableSet<ItemsOfBMI> {
        // 共有プリファレンスから取得した値がNULLの場合は空のMutableSetを返却する
        if (items == null) return mutableSetOf()

        val itemsList = mutableSetOf<ItemsOfBMI>()
        items.forEach {
            it?.let {
                itemsList.add(parseJSON(it))
            }
        }

        // 全ての値が未登録の場合はNULLが返却される。
        return itemsList
    }

    /**
     * itemsListをSharedPreferenceのputStringSet()で使用できるようにコンバートする
     */
    private fun convertJsonSet(): MutableSet<String> {
        var list = mutableSetOf<String>()
        val mapper = ObjectMapper()

        this.itemsList.forEach {
            val jsonString = mapper.writeValueAsString(it)
            list.add(jsonString)

            Log.d("返還後JSON" ,"$jsonString")
        }

        return list
    }

    /**
     * JSON文字列をオブジェクトに変換する
     * @param {json} JSON文字列
     * @return JSONをパースしたオブジェクトが返却される
     */
    private inline fun <reified T : Any> parseJSON(json: String) : T {
        return jacksonObjectMapper().readValue(json ,T::class.java)
    }
}
