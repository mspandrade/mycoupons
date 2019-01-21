package com.projectme.mpandrade.mycoupon.data.mapper

import com.projectme.mpandrade.mycoupon.data.model.Coupon
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import java.util.*

object CouponMapper {

    fun toDataView(coupon: Coupon): CouponData = CouponData(

                id = coupon.id,
                companyName = coupon.companyName,
                description = coupon.description,
                completeIn = coupon.completeIn,
                dueDate = if (coupon.dueDate != null) Date(coupon.dueDate) else null,
                favorite = coupon.favorite,
                image = coupon.image,
                status = coupon.status
    )

    fun toModel(couponData: CouponData): Coupon = Coupon(
            id = couponData.id,
            companyName = couponData.companyName,
            description = couponData.description,
            completeIn = couponData.completeIn,
            dueDate = couponData.dueDate?.time,
            favorite = couponData.favorite,
            image = couponData.image,
            status = couponData.status
    )

}