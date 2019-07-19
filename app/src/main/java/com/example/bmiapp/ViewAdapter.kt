package com.example.bmiapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(private val list: List<RowModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(RecyclerType.fromInt((viewType))) {
            /** SECTION */
            RecyclerType.SECTION -> {
                Log.d("ViewAdapter", "onCreateViewHolder セクションを表示したい")
                val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_section, parent, false)
                return ViewSectionHolder(rowView)
            }

            /** BODY */
            RecyclerType.BODY -> {
                Log.d("ViewAdapter", "onCreateViewHolder ボディを表示したい")
                val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
                return ViewHolder(rowView)
            }

            /** DETAIL */
            RecyclerType.DETAIL -> {
                Log.d("ViewAdapter" ,"onCreateViewHolder メモ表示したい")

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
            is ViewSectionHolder -> {
                val month = "${list[position].item.id.substring(4,6)}月"
                Log.d("ViewAdapter" ,"onBindViewHolder セクションを表示したいです")
                holder.sectionView.text = month
            }

            is ViewHolder -> {
                Log.d("ViewAdapter" ,"onBindViewHolder ボディを表示したいです")

                //表示方法の変更
                val dateForm = "${list[position].item.id.substring(6,8)}日"
                val heightForm = "身長：${list[position].item.strHeight}cm"
                val weightForm = "体重：${list[position].item.strWeight}kg"
                val bmiForm = "BMI：${list[position].item.bmiResult?.substring(0,4)}"

                // holder as ViewHolder
                holder.dateView.text = dateForm
                holder.heightView.text = heightForm
                holder.weightView.text = weightForm
                holder.bmiView.text = bmiForm


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