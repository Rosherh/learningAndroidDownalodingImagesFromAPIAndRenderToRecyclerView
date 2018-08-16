package ru.rosherh.flickerimagegallery.core.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.rosherh.flickerimagegallery.core.ui.BaseHolder
import java.util.*

abstract class BaseRecyclerAdapter<Holder : BaseHolder, Type : Any> : RecyclerView.Adapter<Holder>() {

    private val data: ArrayList<Type> = arrayListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = getHolder()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        bind(p0, p1)
    }

    fun setData(data: List<Type>) {
        if (this.data.isNotEmpty()) this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<Type>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun getData() = data

    abstract fun bind(holder: Holder, position: Int)

    abstract fun getHolder(): Holder

}