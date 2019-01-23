package com.projectme.mpandrade.mycoupon.data.service

import android.content.Context
import android.os.AsyncTask
import com.projectme.mpandrade.mycoupon.data.mapper.CouponMapper
import com.projectme.mpandrade.mycoupon.data.model.Coupon
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

    fun getAll()
            : Observable<List<CouponData>> = doSelect { couponDAO.getAll() }

    fun getFavorites()
            : Observable<List<CouponData>> = doSelect { couponDAO.getFavorites() }

    fun getComplete()
            : Observable<List<CouponData>> = doSelect { couponDAO.getComplete() }

    fun delete(couponData: CouponData) {

        AsyncTask.execute { couponDAO.delete(CouponMapper.toModel(couponData)) }
    }

    fun updateAsync(couponData: CouponData) {
        AsyncTask.execute { couponDAO.update(CouponMapper.toModel(couponData)) }
    }

    fun cleanStorage() {
        AsyncTask.execute { couponDAO.truncate() }
    }

    private fun doSelect(selectFun: () -> List<Coupon>)
            : Observable<List<CouponData>> = Observable.create {

        AsyncTask.execute {

            it.onNext(
                    selectFun.invoke()
                            .map { coupon ->  CouponMapper.toDataView(coupon) }
                            .toList()
            )

            it.onComplete()
        }
    }

}