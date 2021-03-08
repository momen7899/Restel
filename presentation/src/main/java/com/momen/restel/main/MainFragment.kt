package com.momen.restel.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.momen.restel.R
import kotlinx.android.synthetic.main.fragment_main.*

@Suppress("DEPRECATION")
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageView.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToLoginFragment()
            it.findNavController().navigate(action)
        }
    }
}