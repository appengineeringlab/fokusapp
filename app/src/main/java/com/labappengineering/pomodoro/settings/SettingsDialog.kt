package com.labappengineering.pomodoro.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.widget.Toast
import com.labappengineering.pomodoro.R


class SettingsDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_dialog, container, false)
        dialog!!.setTitle("Sample")



        return view
    }

    var doneAction: View.OnClickListener =
        View.OnClickListener { Toast.makeText(activity, "Test", Toast.LENGTH_LONG).show() }
}