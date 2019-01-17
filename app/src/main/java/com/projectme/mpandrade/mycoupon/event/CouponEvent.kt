package com.projectme.mpandrade.mycoupon.event

import com.projectme.mpandrade.mycoupon.data.view.CouponData

interface CouponEvent

data class FavoriteCouponEvent(val couponData: CouponData): CouponEvent

data class UnFavoriteCouponEvent(val couponData: CouponData): CouponEvent