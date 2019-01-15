package com.projectme.mpandrade.mycoupon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.projectme.mpandrade.mycoupon.adapter.CouponListAdapter
import com.projectme.mpandrade.mycoupon.adapter.controller.CouponItemController
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : AppCompatActivity() {

    companion object {

        const val PARAM_COUPON = "coupon"
    }

    private lateinit var coupon: CouponData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        setSupportActionBar(toolbar)

        title = ""

        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.gradient_toolbar_background))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        coupon = intent.getSerializableExtra(PARAM_COUPON) as CouponData

        companyName.text = coupon.companyName.toUpperCase()
        description.text = coupon.description

        initImageCoupon()
        initStatus()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initImageCoupon() {

        Glide.with(this)
                .applyDefaultRequestOptions(CouponItemController.requestOptions)
                .asBitmap()
                .load(coupon.image)
                .into(couponImage)
    }

    private fun initStatus() {

        if (!coupon.isComplete) {

            status.text = getString(R.string.statusContent, coupon.status, coupon.completeIn)
        } else {

            status.visibility = View.GONE
            receive.visibility = View.VISIBLE
        }

    }
}
