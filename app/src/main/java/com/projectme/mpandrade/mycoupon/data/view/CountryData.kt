package com.projectme.mpandrade.mycoupon.data.view

data class CountryData(

        val name: String,
        val subtitle: String? = null,
        var isChecked: Boolean = false,
        val ddi: String,
        val code: String
)