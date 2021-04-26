package com.momen.restel.client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.momen.restel.R
import com.momen.restel.app.App
import com.momen.restel.app.RoomDbModule
import com.momen.restel.client.di.DaggerClientComponent
import com.momen.restel.client.viewmodel.ClientViewModel
import com.momen.restel.client.viewmodel.ClientViewModelFactory
import kotlinx.android.synthetic.main.fragment_client.*
import javax.inject.Inject

class ClientsFragment : Fragment() {
    @Inject
    lateinit var clientViewModelFactory: ClientViewModelFactory
    private var clientViewModel: ClientViewModel? = null
    private val clientAdapter = ClientAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_client, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        injectViewModel()

        super.onActivityCreated(savedInstanceState)

        setUpViewModel()

        setUpComponents()
        subscribeViewModel()
    }

    private fun injectViewModel() {
        DaggerClientComponent.builder()
            .appComponent(App().appComponent)
            .roomModule(RoomDbModule(requireContext()))
            .build()
            .inject(this)
    }

    private fun setUpViewModel() {
        clientViewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
    }

    private fun subscribeViewModel() {
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