package com.projectme.mpandrade.mycoupon.data.view

import java.io.Serializable
import java.util.*

data class CouponData (

        val id: Long,
        val companyName: String,
        val description: String,
        val status: Int,
        val completeIn: Int,
        val dueDate: Date,
        val image: String,
        var favorite: Boolean

)  : Serializable {

    val isComplete get() = status >= completeIn
}