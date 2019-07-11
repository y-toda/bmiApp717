//package com.example.bmiapp
//
//import android.R
//import android.text.format.DateFormat
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class bmiAdapter {
//
//    init {
//        setHasStableIds(true)
//    }
//
//    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
//        val date: TextView = cell.findViewById(R.id.text1)
//        val content: TextView = cell.findViewById(R.id.text2)
//
//    }
//
//    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int ) : ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.simple_list_item_2, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder ( holder: ViewHolder, position: Int) {
////        val schedule: Schedule? = getItem(position)
//        holder.date.text = DateFormat.format("yyyy/MM/dd", bmi?.date)
//        holder.content.text = bmi?.content
//    }
//
//    override fun getItemId(position: Int): Long{
//        return getItem(position)?.id ?: 0
//    }
//
//
//
//
//}
//
//class RecyclerAdapter(
//    private val context: Context,
//    private val states: List<RecyclerState>) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val view = RecyclerItemView(context)
//        return RecyclerItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
//        if(viewHolder is RecyclerItemViewHolder){
//            viewHolder.update(states[position])
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return states.count()
//    }
//}