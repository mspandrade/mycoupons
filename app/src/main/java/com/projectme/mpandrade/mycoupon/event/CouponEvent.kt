package com.projectme.mpandrade.mycoupon.event

import com.projectme.mpandrade.mycoupon.data.view.CouponData

interface CouponEvent

data class FavoriteCouponEvent(
        val couponData: CouponData,
        val fragmentId: String? = null
): CouponEvent

data class UnFavoriteCouponEvent(
        val couponData: CouponData,
        val fragmentId: String? = null
): CouponEvent

data class DeletedCouponEvent(val couponData: CouponData): CouponEvent