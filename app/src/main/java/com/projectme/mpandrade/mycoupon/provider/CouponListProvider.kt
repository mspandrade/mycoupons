package com.projectme.mpandrade.mycoupon.provider

import com.projectme.mpandrade.mycoupon.data.view.CouponData

interface CouponListProvider {

    val couponList : List<CouponData>
}