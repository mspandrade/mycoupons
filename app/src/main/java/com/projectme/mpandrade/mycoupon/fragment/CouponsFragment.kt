package com.projectme.mpandrade.mycoupon.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.projectme.mpandrade.mycoupon.CouponActivity
import com.projectme.mpandrade.mycoupon.R
import com.projectme.mpandrade.mycoupon.adapter.CouponListAdapter
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.provider.CouponListProvider
import java.lang.ref.WeakReference
import java.util.*

open class CouponsFragment : Fragment(), CouponListProvider, CouponListAdapter.Listener {

    private var rcCoupon: RecyclerView? = null

    override val couponList: List<CouponData> get() {

        val couponList = mutableListOf<CouponData>()

        while (couponList.size < 12) couponList.add(instanceMock(couponList.size + 1))

        return couponList
    }

    private fun instanceMock(status: Int): CouponData {

        return CouponData(
                "Habbibs",
                "Na compra de 10 esfirras ganha-se 1",
                status, 15,
                Date(),
                "http://propmark.com.br/static/upload/legacy/thumbs/2014/gtahabibs600.jpg")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? = inflater.inflate(R.layout.fragment_coupon_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcCoupon = view.findViewById(R.id.rcCoupons)

        rcCoupon?.layoutManager = LinearLayoutManager(view.context)
        rcCoupon?.adapter = CouponListAdapter(couponList, WeakReference(this))
    }

    override fun onCardCouponClicked(coupon: CouponData, imageView: ImageView, cardView: CardView) {

        val transitionsElements = arrayOf<Pair<View, String>>(
                Pair(imageView, imageView.transitionName)
        )

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity,
                *transitionsElements
        )

        val intent = Intent(activity , CouponActivity::class.java)

        intent.putExtra(CouponActivity.PARAM_CARD_TRANSITION_NAME, cardView.transitionName)
        intent.putExtra(CouponActivity.PARAM_IMAGE_TRANSITION_NAME, imageView.transitionName)
        intent.putExtra(CouponActivity.PARAM_COUPON, coupon)

        activity?.startActivityFromFragment(this, intent, 200, options.toBundle())
    }
}