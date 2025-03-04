package com.example.practi4eskaya_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FragmentThree : Fragment(R.layout.fragment_three) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonBack = view.findViewById<Button>(R.id.buttonBack)

        // Возврат к FragmentTwo
        buttonBack.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            } else if ((activity as MainActivity).useNavigationApi) {
                findNavController().popBackStack()
            }
        }
    }
}
