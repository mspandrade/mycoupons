package com.projectme.mpandrade.mycoupon.fragment

import com.projectme.mpandrade.mycoupon.data.view.CouponData
import io.reactivex.Observable

class CompleteFragment : CouponsFragment() {

    override val couponsObservable: Observable<List<CouponData>>? get() = couponService?.getComplete()

}