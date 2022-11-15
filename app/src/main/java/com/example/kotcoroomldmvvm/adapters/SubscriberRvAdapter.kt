package com.example.kotcoroomldmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotcoroomldmvvm.R
import com.example.kotcoroomldmvvm.databinding.SubscribersListItemBinding
import com.example.kotcoroomldmvvm.db.Subscriber

class SubscriberRvAdapter(private val callback: (Subscriber) -> Unit):
    RecyclerView.Adapter<SubscriberViewHolder>() {
    private var subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SubscribersListItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.subscribers_list_item, parent, false)
        return SubscriberViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribers.get(position))
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    fun updateItems(newSubscribers: List<Subscriber>){
        this.subscribers.clear()
        this.subscribers.addAll(newSubscribers)
    }
}

class SubscriberViewHolder(val binding: SubscribersListItemBinding, val callback: (Subscriber) -> Unit): RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber){
        binding.tvId.text = "${subscriber.id.toString()}."
        binding.tvName.text = subscriber.name
        binding.tvEmail.text = subscriber.email

        binding.root.setOnClickListener(View.OnClickListener {
            callback.invoke(subscriber)
        })
    }
}