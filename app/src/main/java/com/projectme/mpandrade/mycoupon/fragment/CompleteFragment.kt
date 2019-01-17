package com.projectme.mpandrade.mycoupon.fragment

import com.projectme.mpandrade.mycoupon.data.view.CouponData

class CompleteFragment : CouponsFragment() {

    override val couponList: MutableList<CouponData>
        get() = super.couponList.filter {
            it.isComplete
        }.toMutableList()
}