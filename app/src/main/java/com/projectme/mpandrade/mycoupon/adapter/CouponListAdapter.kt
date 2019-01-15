package com.projectme.mpandrade.mycoupon.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.projectme.mpandrade.mycoupon.R
import com.projectme.mpandrade.mycoupon.adapter.controller.CouponItemController
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import java.lang.ref.WeakReference


class CouponListAdapter(
        private val coupons: List<CouponData>,
        private val listener: WeakReference<Listener>

) : RecyclerView.Adapter<CouponListAdapter.ViewHolder>() {

    interface Listener {

        fun onCardCouponClicked(coupon: CouponData, controller: CouponItemController)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val controller = CouponItemController(view)
    }

    override fun getItemCount(): Int = coupons.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val coupon = coupons[position]
        val controller = viewHolder.controller

        controller.setStatus(coupon.status, coupon.completeIn)
        controller.setCompanyName(coupon.companyName.toUpperCase())
        controller.setDescription(coupon.description)
        controller.setImage(coupon.image)

        controller.cardView.setOnClickListener {
            listener.get()?.onCardCouponClicked(coupon, controller)
        }

        controller.receiveButton.setOnClickListener {
            Toast.makeText(viewHolder.itemView.context, "Todo", Toast.LENGTH_SHORT).show()
        }

        controller.favoriteArea.setOnClickListener {
            Toast.makeText(viewHolder.itemView.context, "Todo", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cell_card_coupon, viewGroup, false)

        return ViewHolder(view)
    }

}