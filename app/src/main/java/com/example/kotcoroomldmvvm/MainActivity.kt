package com.example.kotcoroomldmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotcoroomldmvvm.adapters.SubscriberRvAdapter
import com.example.kotcoroomldmvvm.databinding.ActivityMainBinding
import com.example.kotcoroomldmvvm.db.Subscriber
import com.example.kotcoroomldmvvm.db.SubscriberDataBase
import com.example.kotcoroomldmvvm.repos.SubscriberRepository
import com.example.kotcoroomldmvvm.viewmodel.SubscriberViewModel
import com.example.kotcoroomldmvvm.viewmodel.SubscriberViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : SubscriberViewModel
    private lateinit var adapter : SubscriberRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDataBase.getInstance(application).subscriberDAO
        val repo = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.subscriberViewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        viewModel.message.observe(this, Observer {
            Snackbar.make(binding.root, it.peekContent(), Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun initRecyclerView(){
        binding.rvSubscriberList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = SubscriberRvAdapter() { selectedItem: Subscriber ->
            listItemClicked(
                selectedItem
            )
        }
        binding.rvSubscriberList.adapter = adapter

        viewModel.subscribers.observe(this, Observer {
            Log.d("MainActivity","Subscriber $it")
            adapter.updateItems(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber){
        Log.d("MainActivity","Clicked ${subscriber.name}")
        viewModel.initUpdateOrDelete(subscriber)
    }
}