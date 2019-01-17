package com.projectme.mpandrade.mycoupon.fragment

import com.projectme.mpandrade.mycoupon.data.view.CouponData

class FavoriteFragment : CouponsFragment() {

    override val couponList: List<CouponData>
        get() = super.couponList.filter {
            it.favorite
        }
}