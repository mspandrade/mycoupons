package com.projectme.mpandrade.mycoupon.data.dao

import androidx.room.*
import com.projectme.mpandrade.mycoupon.data.model.Coupon

@Dao
interface CouponDAO {

    companion object {

        const val TABLE = "coupon"
    }

    @Query("SELECT * FROM $TABLE LIMIT :limit OFFSET :offset")
    fun getAll(offset: Int, limit: Int): List<Coupon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg coupon: Coupon)

    @Delete
    fun delete(coupon: Coupon)

    @Query("DELETE FROM $TABLE")
    fun truncate()

}