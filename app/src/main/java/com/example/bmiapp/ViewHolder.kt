package com.example.bmiapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val dateView : TextView = itemView.findViewById(R.id.row_date)
    val heightView: TextView = itemView.findViewById(R.id.row_height)
    val weightView: TextView = itemView.findViewById(R.id.row_weight)
    val bmiView: TextView = itemView.findViewById(R.id.row_bmi)
}

class ViewSectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val sectionView: TextView = itemView.findViewById(R.id.section)

}

class ViewDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val contentsView: TextView = itemView.findViewById(R.id.contentsDetail)
}