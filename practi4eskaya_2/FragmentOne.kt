package com.example.practi4eskaya_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FragmentOne : Fragment(R.layout.fragment_one) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<Button>(R.id.buttonNext)
        buttonNext.setOnClickListener {
            val mainActivity = activity as MainActivity

            if (mainActivity.useNavigationApi) {
                findNavController().navigate(R.id.action_fragmentOne_to_fragmentTwo)
            } else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FragmentTwo())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}
