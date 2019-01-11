package com.projectme.mpandrade.mycoupon.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.projectme.mpandrade.mycoupon.R
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import java.lang.ref.WeakReference


class CouponListAdapter(
        private val coupons: List<CouponData>,
        private val listener: WeakReference<Listener>

) : RecyclerView.Adapter<CouponListAdapter.ViewHolder>() {

    interface Listener {

        fun onCardCouponClicked(coupon: CouponData, imageView: ImageView, cardView: CardView)
    }

    companion object {

        var instanceId: Int = 0

        var requestOptions = RequestOptions()
                .centerCrop()
                .placeholder(android.R.drawable.spinner_background)
                .error(R.drawable.shape_rectangle)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    init {
        instanceId++
    }

    override fun getItemCount(): Int = coupons.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val coupon = coupons[position]
        val view = viewHolder.itemView

        val status = "${coupon.status}/${coupon.completeIn}"

        val cardView = view.findViewById<CardView>(R.id.cardCoupon)

        view.findViewById<TextView>(R.id.companyName)?.text = coupon.companyName.toUpperCase()
        view.findViewById<TextView>(R.id.description)?.text = coupon.description
        view.findViewById<TextView>(R.id.status)?.text = status

        val imageView = view.findViewById<ImageView>(R.id.cardImage)
        val id = "_${instanceId}_$position"

        imageView.transitionName = view.context.getString(R.string.transitionImageCardCoupon) + id
        cardView.transitionName = view.context.getString(R.string.transitionCardCoupon) + id

        if (imageView != null) {

            Glide.with(view.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .asBitmap()
                    .load(coupon.image)
                    .into(imageView)
        }

        cardView?.setOnClickListener { listener.get()?.onCardCouponClicked(coupon, imageView, cardView) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_card_coupon, viewGroup, false)

        return ViewHolder(view)
    }

}