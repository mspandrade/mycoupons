package com.projectme.mpandrade.mycoupon.data

import android.content.Context
import androidx.room.Room

object DatabaseFactory {

    private const val DB_NAME = "my-coupon"

    fun instanceDB(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
    ).build()

}