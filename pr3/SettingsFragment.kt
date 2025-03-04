package com.example.pr3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import com.example.pr3.vm.SettingsViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var viewModel: SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        val checkBox = view.findViewById<CheckBox>(R.id.notificationsCheckBox)

        viewModel.run {
            notificationsEnabled.observe(viewLifecycleOwner) { enabled ->
                checkBox.isChecked = enabled
            }
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationsEnabled(isChecked)
        }
    }
}
