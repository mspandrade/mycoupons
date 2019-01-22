package com.projectme.mpandrade.mycoupon.fragment

import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.event.FavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.event.UnFavoriteCouponEvent
import io.reactivex.Observable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe



class FavoriteFragment : CouponsFragment() {

    override val couponsObservable: Observable<List<CouponData>>? get() = couponService?.getFavorites()

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteEvent(event: FavoriteCouponEvent) {

        if (adapter?.coupons != null) {
            adapter?.coupons?.add(event.couponData)
            adapter?.notifyItemInserted(adapter?.itemCount!!)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUnFavoriteEvent(event: UnFavoriteCouponEvent) {

        if (adapter?.coupons != null) {

            val coupons = adapter?.coupons!!

            val index = coupons.indexOfFirst { it.id == event.couponData.id }

            if (index >= 0) {
                coupons.remove(event.couponData)
                adapter?.notifyItemRemoved(index)
            }
        }
    }
}