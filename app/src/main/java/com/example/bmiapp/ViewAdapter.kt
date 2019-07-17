package com.example.bmiapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(private val list: List<RowModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(RecyclerType.fromInt((viewType))) {
            /** BODY */
            RecyclerType.BODY -> {
                val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
                return ViewHolder(rowView)

            }

            /** DETAIL */
            RecyclerType.DETAIL -> {
                Log.d("ViewAdapter" ,"onBindViewHolder メモ表示したい")

                val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_contents, parent, false)
                return ViewDetailHolder(rowView)
            }
        }

    }


    /**
     * RecyclerView.ViewHolder : 1行分のViewの参照を保持するもの
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                Log.d("ViewAdapter" ,"onBindViewHolder メモは表示しないです")

                // holder as ViewHolder
                holder.dateView.text = list[position].item.id
                holder.heightView.text = list[position].item.strHeight
                holder.weightView.text = list[position].item.strWeight
                holder.bmiView.text = list[position].item.bmiResult
            }

            is ViewDetailHolder -> {
                Log.d("ViewAdapter" ,"onBindViewHolder メモ表示したいです")

//                holder.dateView.text = list[position].item.id
//                holder.heightView.text = list[position].item.strHeight
//                holder.weightView.text = list[position].item.strWeight
//                holder.bmiView.text = list[position].item.bmiResult
                holder.contentsView.text = list[position].item.contents
            }

            else -> {

            }
        }
    }

    //これいれないとコメント返さない
    override fun getItemViewType(position: Int): Int {
        return list[position].type.int
    }

    override fun getItemCount(): Int {
        return list.size
    }
}