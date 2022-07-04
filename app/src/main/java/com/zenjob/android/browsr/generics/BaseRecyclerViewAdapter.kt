package com.zenjob.android.browsr.generics

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
   private var list: ArrayList<T>? = ArrayList<T>()
    protected lateinit var itemClickListener: OnItemClickListener<T>

    fun addItems(items: List<T>) {
        this.list?.addAll(items)
        reload()
    }


    fun addItem(items: T) {
        this.list?.add(items)
        reload()
    }

    fun clear() {
        this.list?.clear()
        reload()
    }

    fun getItem(position: Int): T? {
        return this.list?.get(position)
    }


    open fun getPosition(item: T): Int {
        if (list!!.size == 0) return -1
        var i = 0
        while (list!![i] != item) {
            i++
        }
        return i
    }

    open fun removeItem(item: T): Int {
        val pos: Int = getPosition(item)
        list!!.removeAt(pos)
        notifyItemRemoved(pos)
        return pos
    }

    open fun remove(position: Int) {
        list!!.removeAt(position)
        notifyDataSetChanged()
    }

    fun getLists(): List<T> {
        return this.list!!
    }

    fun getList(): ArrayList<T> {
        return this.list!!
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = list!!.size

    private fun reload() {
        Handler(Looper.getMainLooper()).post { notifyDataSetChanged() }
    }

    fun addItem(items: List<T>) {
        this.list?.addAll(items)
        reload()
    }


    interface OnItemClickListener<T> {
        fun onItemClick(listitem: T, position: Int, view: View?)
    }

}
