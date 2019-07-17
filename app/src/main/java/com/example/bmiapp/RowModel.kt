package com.example.bmiapp

import com.example.entity.ItemsOfBMI

data class RowModel(val type: RecyclerType ,val item: ItemsOfBMI)


////なくてもいいらしい
/**
 * RecyclerTypeを用いると"SECTION"でも"0"でも使える。
 */
enum class RecyclerType(val int: Int) {

//    SECTION(0),
    BODY(1),
    DETAIL(2);

    companion object {
        // Intからenumへの変換
        fun fromInt(int: Int): RecyclerType {
            return values().firstOrNull { it.int == int }
                ?: BODY
        }
    }
}
