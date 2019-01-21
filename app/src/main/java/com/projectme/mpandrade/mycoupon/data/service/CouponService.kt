package com.projectme.mpandrade.mycoupon.data.service

import android.content.Context
import android.os.AsyncTask
import com.projectme.mpandrade.mycoupon.data.mapper.CouponMapper
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import io.reactivex.Observable

class CouponService(context: Context) : Service(context) {

    private val couponDAO get() = db.coupon()

    fun insertAll(vararg couponsData: CouponData) {

        AsyncTask.execute {

            couponDAO.insertAll(
                    *couponsData.toMutableList()
                            .map { CouponMapper.toModel(it) }
                            .toTypedArray()
            )
        }
    }

    fun getAll(offset: Int = 0, limit: Int = 15): Observable<List<CouponData>> = Observable.create {

        it.onNext(
                couponDAO.getAll(offset, limit)
                        .map { coupon ->  CouponMapper.toDataView(coupon) }
                        .toList()
        )

        it.onComplete()
    }

    fun delete(couponData: CouponData) {

        couponDAO.delete(CouponMapper.toModel(couponData))
    }

    fun cleanStorage() {
        AsyncTask.execute { couponDAO.truncate() }
    }

}