package com.labappengineering.pomodoro.settings

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.labappengineering.pomodoro.R

import kotlinx.android.synthetic.main.settings_list_item.view.*

import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder

import android.widget.Toast





class SettingsAdapter(val items: MutableLiveData<ArrayList<SessionItem>>,
                      val context: Context,
                      val clickListener: (SessionItem) -> Unit,
                      val colorClickListener: (SessionItem) -> Unit)
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
        holder.itemView.setOnClickListener {
            items.value!![position].index = position
            clickListener(items.value!![position])
        }
        holder.fl.setOnClickListener{
            items.value!![position].index = position
            colorClickListener(items.value!![position])
        }
    }
}

class ViewHolder ( view: View): RecyclerView.ViewHolder(view){
    val tv1 = view.settings_li_tv_name
    val tv2 = view.settings_li_tv_value
    val fl = view.settings_fl_color
}