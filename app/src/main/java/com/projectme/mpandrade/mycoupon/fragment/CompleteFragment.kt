package com.projectme.mpandrade.mycoupon.fragment

import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.event.FavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.event.UnFavoriteCouponEvent
import io.reactivex.Observable
import org.greenrobot.eventbus.Subscribe

class CompleteFragment : BaseCouponFragment() {

    override val couponsObservable: Observable<List<CouponData>>? get() = couponService?.getComplete()

    @Subscribe
    fun onFavoriteEvent(event: FavoriteCouponEvent) {
        if (event.fragmentId != toString()) updateCouponInAdapter(event.couponData)
    }

    @Subscribe
    fun onUnFavoriteEvent(event: UnFavoriteCouponEvent) {
        if (event.fragmentId != toString()) updateCouponInAdapter(event.couponData)
    }
}