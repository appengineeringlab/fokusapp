package com.labappengineering.pomodoro.settings

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import kotlinx.android.synthetic.main.settings_list_item.view.*




class SettingsAdapter(val items: MutableLiveData<List<SessionItem>>,
                      val context: Context,
                      val clickListener: (SessionItem) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.settings_list_item,
            parent, false))
    }

    override fun getItemCount(): Int {
        return items.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv1.text = items.value!![position].name

        if(items.value!![position].name.toLowerCase().contains("color")){
            holder.fl.setBackgroundColor(Color.parseColor(items.value!![position].value))
            holder.tv2.text = ""
        } else {
            holder.tv2.text = items.value!![position].value
        }
        holder.itemView.setOnClickListener { clickListener(items.value!![position])}
    }
}

class ViewHolder ( view: View): RecyclerView.ViewHolder(view){
    val tv1 = view.settings_li_tv_name
    val tv2 = view.settings_li_tv_value
    val fl = view.settings_fl_color
}