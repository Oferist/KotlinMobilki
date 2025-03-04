package com.example.practi4eskaya_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FragmentTwo : Fragment(R.layout.fragment_two) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<Button>(R.id.buttonNext)
        val buttonBack = view.findViewById<Button>(R.id.buttonBack)

        // Переход к FragmentThree
        buttonNext.setOnClickListener {
            val mainActivity = activity as MainActivity

            if (mainActivity.useNavigationApi) {
                findNavController().navigate(R.id.action_fragmentTwo_to_fragmentThree)
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FragmentThree())
                    .addToBackStack(null)
                    .commit()
            }
        }

        // Возврат к FragmentOne
        buttonBack.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            } else if ((activity as MainActivity).useNavigationApi) {
                findNavController().popBackStack()
            }
        }
    }
}
