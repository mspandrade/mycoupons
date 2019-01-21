package com.projectme.mpandrade.mycoupon.data.service

import android.content.Context
import com.projectme.mpandrade.mycoupon.data.DatabaseFactory

open class Service(context: Context) {

    private val dbInstance = DatabaseFactory.instanceDB(context)

    protected val db = dbInstance

}