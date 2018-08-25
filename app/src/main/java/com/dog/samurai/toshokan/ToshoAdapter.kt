package com.dog.samurai.toshokan

import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.list_item.view.*

class ToshoAdapter : RecyclerView.Adapter<ToshoAdapter.ViewHolder>() {

    var toshoData: List<ToshoModel> = listOf()
    var listener: ToshoAdapter.Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = toshoData.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.apply {

            tosho_name.text = toshoData[position].systemname
            tosho_name.append(" ")
            tosho_name.append(toshoData[position].short)

            postal_code.text = "〒:"
            postal_code.append(toshoData[position].post)
            address.text = toshoData[position].address
            tel.text = toshoData[position].tel
            url.text = toshoData[position].url_pc

            category.text = when (toshoData[position].category) {
                Category.SMALL.name -> Category.SMALL.type
                Category.MEDIUM.name -> Category.MEDIUM.type
                Category.LARGE.name -> Category.LARGE.type
                Category.UNIV.name -> Category.UNIV.type
                Category.SPECIAL.name -> Category.SPECIAL.type
                Category.BM.name -> Category.BM.type
                else -> ""
            }

            if (!toshoData[position].url_pc.isNullOrBlank()) {
                web.setOnClickListener {
                    listener?.onClickWeb(toshoData[position].url_pc!!)
                }
            }

            if (!toshoData[position].geocode.isNullOrBlank()) {
                location.setOnClickListener {
                    listener?.onClickMap(toshoData[position].geocode!!, toshoData[position].address)
                }
            }

            if (!toshoData[position].tel.isNullOrBlank()) {
                telephone.setOnClickListener {
                    listener?.onClickPhone(toshoData[position].tel!!)
                }
            }
        }

        if (lastPosition < viewHolder.adapterPosition) {
            startAnimation(viewHolder.view, viewHolder.adapterPosition)
            lastPosition = viewHolder.adapterPosition
        }

    }

    var lastPosition = 0

    fun startAnimation(view: View, position: Int) {
        if (position > 2) {
            val animation = AnimationUtils.loadAnimation(view.context, R.anim.abc_slide_in_bottom)
            animation.interpolator = LinearOutSlowInInterpolator()
            animation.startTime = 500
            view.startAnimation(animation)
        }

    }

    private var isOpenAddress = false

    fun setItem(items: List<ToshoModel>) {
        toshoData = items
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun onClickWeb(url: String)
        fun onClickMap(geocode: String, address: String)
        fun onClickPhone(number: String)
    }

    enum class Category(val type: String) {
        SMALL("図書館・公民館"),
        MEDIUM("図書館（地域）"),
        LARGE("図書館（広域）"),
        UNIV("大学"),
        SPECIAL("専門"),
        BM("移動図書館")
    }

}