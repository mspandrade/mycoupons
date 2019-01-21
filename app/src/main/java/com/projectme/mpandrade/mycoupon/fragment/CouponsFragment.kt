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
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import java.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


open class CouponsFragment : Fragment(), CouponListProvider, CouponListAdapter.Listener {

    private var rcCoupon: RecyclerView? = null

    private val coupons = mutableListOf<CouponData>()
    override val couponList get() = coupons

    val adapter: CouponListAdapter? get() = rcCoupon?.adapter as? CouponListAdapter
    private val couponService = if (context != null) CouponService(context!!) else null

    private var disposableRequisition : Disposable? = null

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

        disposableRequisition = couponService?.getAll()?.subscribe {

            coupons.clear()
            coupons.addAll(it)
            adapter?.notifyDataSetChanged()
        }
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

    override fun onCouponFavorite(coupon: CouponData) {
        EventBus.getDefault().post(FavoriteCouponEvent(coupon))
    }

    override fun onCouponUnFavorite(coupon: CouponData) {
        EventBus.getDefault().post(UnFavoriteCouponEvent(coupon))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeletedCouponEvent(deletedCouponEvent: DeletedCouponEvent) {
        val index = couponList.indexOfFirst { deletedCouponEvent.couponData.id == it.id }
        couponList.removeAt(index)
        adapter?.notifyDataSetChanged()
    }
}