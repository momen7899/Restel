package com.momen.restel.passenger.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.momen.restel.R
import kotlinx.android.synthetic.main.fragment_passenger.*

class PassengerFragment : Fragment() {

    private val passengerAdapter = PassengerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_passenger, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpComponents()
    }

    private fun setUpComponents() {
        setUpRecycler()

    }

    private fun setUpRecycler() {
        passengerRecycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        passengerRecycle.adapter = passengerAdapter
    }
}