package com.labappengineering.pomodoro.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import kotlinx.android.synthetic.main.settings_list_item.view.*




class SettingsAdapter(val items: LinkedHashMap<String, String>,
                      val context: Context,
                      val clickListener: (HashMap<String, String>) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.settings_list_item,
            parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var keyList: ArrayList<String> = ArrayList(items.size)
        var valueList: ArrayList<String> = ArrayList(items.size)
        val keys = items.keys.toCollection(keyList)
        val values = items.values.toCollection(valueList)
        holder.tv1.text = keys[position]
        holder.tv2.text = values[position]
//        val retMap = LinkedHashMap<String, String>()
//        retMap[keys[position]] = values[position]
//        holder.itemView.setOnClickListener { clickListener()}
    }
}

class ViewHolder ( view: View): RecyclerView.ViewHolder(view){
    val tv1 = view.settings_li_tv_name
    val tv2 = view.settings_li_tv_value
}