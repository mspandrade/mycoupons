package com.projectme.mpandrade.mycoupon.fragment

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.projectme.mpandrade.mycoupon.adapter.CouponListAdapter
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.event.FavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.event.UnFavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.provider.CouponListProvider
import io.reactivex.Observable
import org.greenrobot.eventbus.Subscribe


open class CouponsFragment : BaseCouponFragment(), CouponListProvider, CouponListAdapter.Listener, SwipeRefreshLayout.OnRefreshListener {

    override val couponsObservable: Observable<List<CouponData>>? get() = couponService?.getAll()

    @Subscribe
    fun onFavoriteEventArrived(event: FavoriteCouponEvent) {
        couponService?.updateAsync(event.couponData)
        if (event.fragmentId != toString()) updateCouponInAdapter(event.couponData)
    }

    @Subscribe
    fun onUnFavoriteEventArrived(event: UnFavoriteCouponEvent) {
        couponService?.updateAsync(event.couponData)
        if (event.fragmentId != toString()) updateCouponInAdapter(event.couponData)
    }

}