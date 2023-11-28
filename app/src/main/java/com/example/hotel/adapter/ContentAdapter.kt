package com.example.hotel.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adapterdelegate.adapter.ViewType
import com.example.adapterdelegate.adapter.ViewTypeDelegateAdapter
import com.example.hotel.model.Room

class ContentAdapter(itemClickListener: ((Room) -> Unit)? = null)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<ViewType>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(AdapterConstants.Photo, PagerDelegateAdapter())
        delegateAdapters.put(AdapterConstants.Room, RoomsDelegateAdapter(itemClickListener))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }

}