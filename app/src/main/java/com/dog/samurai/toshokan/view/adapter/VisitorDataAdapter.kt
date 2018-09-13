package com.dog.samurai.toshokan.view.adapter

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dog.samurai.toshokan.R
import com.dog.samurai.toshokan.model.Data
import kotlinx.android.synthetic.main.value_item.view.*

class VisitorDataAdapter : RecyclerView.Adapter<VisitorDataAdapter.ViewHolder>() {

    private lateinit var detailDatas: List<Data>

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.value_item, parent, false))
    }

    override fun getItemCount(): Int = detailDatas.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {
            year_text.text = detailDatas[position].year.toString()
            year_text.append("年")
            quarter_text.text = detailDatas[position].quarter.toString()
            quarter_text.append("期")
            visitor_text.text = detailDatas[position].value.toString()
            visitor_text.append("人")
        }
    }

    fun setItem(items: List<Data>) {
        detailDatas = items
        notifyDataSetChanged()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        ViewCompat.animate(holder.view).cancel()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}