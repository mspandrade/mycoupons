package com.projectme.mpandrade.mycoupon.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projectme.mpandrade.mycoupon.data.dao.CouponDAO
import com.projectme.mpandrade.mycoupon.data.model.Coupon

@Database(
        entities = [
            Coupon::class
        ],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coupon(): CouponDAO
}