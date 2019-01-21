package com.projectme.mpandrade.mycoupon.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coupon(

        @PrimaryKey(autoGenerate = true) val id: Long,
        @ColumnInfo(name = "company_name") val companyName: String,
        val description: String,
        val status: Int,
        @ColumnInfo(name = "complete_in") val completeIn: Int,
        @ColumnInfo(name = "due_date") val dueDate: Long?,
        val image: String,
        var favorite: Boolean
)