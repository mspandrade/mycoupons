package com.projectme.mpandrade.mycoupon.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.projectme.mpandrade.mycoupon.CouponActivity
import com.projectme.mpandrade.mycoupon.R
import com.projectme.mpandrade.mycoupon.adapter.CouponListAdapter
import com.projectme.mpandrade.mycoupon.adapter.controller.CouponItemController
import com.projectme.mpandrade.mycoupon.data.service.CouponService
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.event.DeletedCouponEvent
import com.projectme.mpandrade.mycoupon.event.FavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.event.UnFavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.provider.CouponListProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.ref.WeakReference

abstract class BaseCouponFragment : Fragment(), CouponListProvider, CouponListAdapter.Listener, SwipeRefreshLayout.OnRefreshListener {

    private var rcCoupon: RecyclerView? = null
    protected val coupons = mutableListOf<CouponData>()
    private var internalCouponService: CouponService? = null
    private var swipeContainer: SwipeRefreshLayout? = null

    val adapter: CouponListAdapter? get() = rcCoupon?.adapter as? CouponListAdapter

    override val couponService: CouponService? get() = internalCouponService

    override val compositeDisposable = CompositeDisposable()
    override val couponList get() = coupons

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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

        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer?.setOnRefreshListener(this)
        swipeContainer?.setColorSchemeResources(R.color.colorAccent)

        if (context != null) {
            internalCouponService = CouponService(context!!)
            onRefresh()
        }
    }

    override fun onRefresh() {

        swipeContainer?.isRefreshing = true

        val disposable = couponsObservable?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {

                    coupons.clear()
                    coupons.addAll(it)
                    adapter?.notifyDataSetChanged()
                    swipeContainer?.isRefreshing = false
                }

        if (disposable != null) compositeDisposable.add(disposable)
    }

    override fun onCardCouponClicked(coupon: CouponData, controller: CouponItemController) {

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity,
                *controller.transitionsElements
        )

        val intent = Intent(activity , CouponActivity::class.java)

        intent.putExtra(CouponActivity.PARAM_COUPON, coupon)

        activity?.startActivityFromFragment(this, intent, 200, options.toBundle())
    }

    override fun onCouponFavorite(index: Int, coupon: CouponData) {

        val event = if (coupon.favorite) {

            FavoriteCouponEvent(coupon, toString())
        } else {

            UnFavoriteCouponEvent(coupon, toString())
        }
        couponService?.updateAsync(coupon)
        EventBus.getDefault().post(event)
    }

    @Subscribe
    fun onDeletedCouponEvent(deletedCouponEvent: DeletedCouponEvent) {
        val index = couponList.indexOfFirst { deletedCouponEvent.couponData.id == it.id }
        couponList.removeAt(index)
        adapter?.notifyDataSetChanged()
        couponService?.delete(deletedCouponEvent.couponData)
    }

    protected fun updateCouponInAdapter(coupon: CouponData) {

        val position = coupons.indexOfFirst { coupon.id == it.id }
        coupons[position] = coupon
        adapter?.notifyItemChanged(position)
    }

}