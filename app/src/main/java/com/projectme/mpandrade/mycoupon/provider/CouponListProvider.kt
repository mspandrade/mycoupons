package com.projectme.mpandrade.mycoupon.provider

import com.projectme.mpandrade.mycoupon.data.service.CouponService
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

interface CouponListProvider {

    val couponList : List<CouponData>

    val couponsObservable: Observable<List<CouponData>>?

    val compositeDisposable: CompositeDisposable

    val couponService: CouponService?
}