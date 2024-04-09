package com.example.hotel.presentation.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.data.models.RoomDTO
import com.example.hotel.domain.models.Room

class ContentAdapter(itemClickListener: ((Room) -> Unit)? = null)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<ViewType>()
        set(value) {
            val callBack = DiffCallBack(items, value)
            val diffResult = DiffUtil.calculateDiff(callBack)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(PHOTO, PagerDelegateAdapter())
        delegateAdapters.put(ROOM, RoomsDelegateAdapter(itemClickListener))
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
        return this.items[position].getViewType()
    }

    companion object{
        const val PHOTO = 1
        const val ROOM = 2
    }
}