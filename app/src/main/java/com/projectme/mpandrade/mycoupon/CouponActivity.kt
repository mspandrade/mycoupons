package com.projectme.mpandrade.mycoupon

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.projectme.mpandrade.mycoupon.adapter.controller.CouponItemController
import com.projectme.mpandrade.mycoupon.data.service.CouponService
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.event.DeletedCouponEvent
import com.projectme.mpandrade.mycoupon.event.FavoriteCouponEvent
import com.projectme.mpandrade.mycoupon.event.UnFavoriteCouponEvent
import kotlinx.android.synthetic.main.activity_coupon.*
import org.greenrobot.eventbus.EventBus

class CouponActivity : AppCompatActivity() {

    companion object {

        const val PARAM_COUPON = "coupon"
    }

    private lateinit var coupon: CouponData
    private lateinit var couponService: CouponService

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)

        title = getString(R.string.coupon)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 6F

        couponService = CouponService(this)
        coupon = intent.getSerializableExtra(PARAM_COUPON) as CouponData

        companyName.text = coupon.companyName.toUpperCase()
        description.text = coupon.description

        initImageCoupon()
        initStatus()
        initDueDate()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_coupon, menu)

        val item = menu?.findItem(R.id.favorite)

        if (!coupon.favorite) {

            item?.icon = getDrawable(R.drawable.ic_not_favorite_coupon)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.favorite -> {

                coupon.favorite = !coupon.favorite

                item.icon = getDrawable(

                    if (coupon.favorite) {

                        EventBus.getDefault().post(FavoriteCouponEvent(coupon))

                        R.drawable.ic_favorite_coupon
                    } else {

                        EventBus.getDefault().post(UnFavoriteCouponEvent(coupon))
                        R.drawable.ic_not_favorite_coupon
                    }
                )
            }

            R.id.delete -> {

                val dialog = AlertDialog.Builder(this)
                                    .setMessage(R.string.alertDeleteCouponMessage)
                                    .setNegativeButton(R.string.alertPositiveResponse) { _, _ ->

                                        couponService.delete(coupon)

                                        EventBus.getDefault().post(DeletedCouponEvent(coupon))
                                        onSupportNavigateUp()
                                    }
                                    .setPositiveButton(R.string.alertNegativeResponse) { _, _ -> }
                                    .setCancelable(true)
                                    .create()

                dialog?.show()

                dialog?.getButton(AlertDialog.BUTTON_POSITIVE)
                        ?.setTextColor(ContextCompat.getColor(this, R.color.textPositiveColor))
            }

            android.R.id.home -> onSupportNavigateUp()

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
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

            statusArea.visibility = View.GONE
            receive.visibility = View.VISIBLE
        }

    }

    private fun initDueDate() {

        dueDate.text =  if (coupon.dueDate == null) {
            getString(R.string.dueDateUndefined)
        } else {
            getString(R.string.dueDateDatePattern).format(coupon.dueDate)
        }
    }
}
