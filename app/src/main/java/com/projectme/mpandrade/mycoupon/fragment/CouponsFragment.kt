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
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.event.FavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.event.UnFavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.provider.CouponListProvider
import java.lang.ref.WeakReference
import java.util.*
import org.greenrobot.eventbus.EventBus



open class CouponsFragment : Fragment(), CouponListProvider, CouponListAdapter.Listener {

    private var rcCoupon: RecyclerView? = null

    val adapter: CouponListAdapter? get() = rcCoupon?.adapter as? CouponListAdapter

    override val couponList: MutableList<CouponData> get() {

        return mutableListOf(
                CouponData(
                        1,
                        "Habbibs",
                "Na compra de 10 esfirras ganha-se 1",
                10, 15,
                null,
                "http://propmark.com.br/static/upload/legacy/thumbs/2014/gtahabibs600.jpg",
                        true),

                CouponData(
                        2,
                        "McDonald's",
                        "Na compra de 1 lanche ganha-se outro",
                        0, 1,
                        Date(),
                        "https://geekpublicitario.com.br/wp-content/uploads/2016/03/promocao-big-tasty-2-em-1-mcdonalds-blog-geek-publicitario.jpg",
                        false),

                CouponData(
                        3,
                        "Divino Fogão",
                        "A cada 5 refeições ganha-se um brinde",
                        5, 5,
                        Date(),
                        "https://anrbrasil.org.br/wp-content/uploads/2018/02/Post_03b_Ferdinand_600x600px.png",
                        true),

                CouponData(
                        4,
                        "Burguer King",
                        "Na compra de 1 lanche ganha-se outro",
                        0, 1,
                        Date(),
                        "https://static.pelando.com.br/live/threads/thread_full_screen/default/248242_1.jpg",
                        false),

                CouponData(
                        5,
                        "Pizza Hut",
                        "Na compra de duas pizzas ganha-se um refrigerante",
                        0, 2,
                        Date(),
                        "https://i.pinimg.com/originals/cf/de/ab/cfdeabd2b571016773f3c68edfbb8262.jpg",
                        false),

                CouponData(
                        6,
                        "KFC",
                        "Na compra de 1 lanche ganha-se outro",
                        0, 1,
                        Date(),
                        "http://guiadopreço.com/wp-content/uploads/2017/01/kfc--e1483623133166.jpg",
                        true)
        )
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
}