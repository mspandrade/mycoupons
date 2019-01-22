package com.projectme.mpandrade.mycoupon.fragment

import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.event.FavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.event.UnFavoriteCouponEvent
import io.reactivex.Observable
import org.greenrobot.eventbus.Subscribe



class FavoriteFragment : BaseCouponFragment() {

    override val couponsObservable: Observable<List<CouponData>>? get() = couponService?.getFavorites()

    @Subscribe
    fun onFavoriteEvent(event: FavoriteCouponEvent) {

        if (adapter?.coupons != null) {
            adapter?.coupons?.add(event.couponData)
            adapter?.notifyItemInserted(adapter?.itemCount!!)
        }
    }

    @Subscribe
    fun onUnFavoriteEvent(event: UnFavoriteCouponEvent) {

        if (adapter?.coupons != null) {

            val index = coupons.indexOfFirst { it.id == event.couponData.id }

            if (index >= 0) {
                coupons.removeAt(index)
                adapter?.notifyItemRemoved(index)
            }
        }
    }
}