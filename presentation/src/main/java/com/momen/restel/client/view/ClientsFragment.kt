package com.momen.restel.client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.momen.restel.R

class ClientsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_client, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        injectViewModel()

        super.onActivityCreated(savedInstanceState)

//        setUpViewModel()

//        setUpComponents()
//        subscribeViewModel()
    }
}