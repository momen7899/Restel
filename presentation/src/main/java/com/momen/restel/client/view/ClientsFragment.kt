package com.momen.restel.client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.momen.restel.R
import kotlinx.android.synthetic.main.fragment_client.*

class ClientsFragment : Fragment() {

    private val clientAdapter = ClientAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_client, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        injectViewModel()

        super.onActivityCreated(savedInstanceState)

//        setUpViewModel()

        setUpComponents()
//        subscribeViewModel()
    }

    private fun setUpComponents() {
        setUpUpRecycler()
    }

    private fun setUpUpRecycler() {
        clientRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        clientRecycle.adapter = clientAdapter
    }

}