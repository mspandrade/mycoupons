package com.projectme.mpandrade.mycoupon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.projectme.mpandrade.mycoupon.adapter.CouponListAdapter
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : AppCompatActivity() {

    companion object {

        const val PARAM_COUPON = "coupon"
        const val PARAM_IMAGE_TRANSITION_NAME = "imageCardCouponTransition"
        const val PARAM_CARD_TRANSITION_NAME = "cardCouponTransition"
    }

    private var coupon: CouponData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        setSupportActionBar(toolbar)

        title = ""

        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.gradient_toolbar_background))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        coupon = intent.getSerializableExtra(PARAM_COUPON) as CouponData

        couponImage.transitionName = intent.getStringExtra(PARAM_IMAGE_TRANSITION_NAME)

        if (coupon?.image != null) {

            Glide.with(this)
                    .applyDefaultRequestOptions(CouponListAdapter.requestOptions)
                    .asBitmap()
                    .load(coupon?.image)
                    .into(couponImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
