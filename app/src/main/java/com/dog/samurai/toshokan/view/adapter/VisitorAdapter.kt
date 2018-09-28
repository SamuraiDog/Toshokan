package com.dog.samurai.toshokan.view.adapter

import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.dog.samurai.toshokan.R
import com.dog.samurai.toshokan.model.VisitorResult
import kotlinx.android.synthetic.main.list_item.view.*

class VisitorAdapter : RecyclerView.Adapter<VisitorAdapter.ViewHolder>() {

    private lateinit var fromData: VisitorResult
    private var lastPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = fromData.result.changes.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {

//            title.text = fromData.result.changes[viewHolder.adapterPosition].countryName
//            val visitorDataAdapter = VisitorDataAdapter()
//
//            visitor_recycler.apply {
//                layoutManager = LinearLayoutManager(context)
//                adapter = visitorDataAdapter
//                setHasFixedSize(true)
//            }
//
//            visitorDataAdapter.setItem(fromData.result.changes[position].data)
        }

        if (lastPosition < viewHolder.adapterPosition) {
            startAnimation(viewHolder.view, viewHolder.adapterPosition)
            lastPosition = viewHolder.adapterPosition
        }

    }

    private fun startAnimation(view: View, position: Int) {
        if (position > 2) {
//            val animation = AnimationUtils.loadAnimation(view.context, R.anim.fade_in)
//            animation.interpolator = LinearOutSlowInInterpolator()
//            animation.startTime = 500
//            view.startAnimation(animation)
        }
    }

    fun setItem(items: VisitorResult) {
        fromData = items
        notifyDataSetChanged()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        ViewCompat.animate(holder.view).cancel()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
