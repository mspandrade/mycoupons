package com.projectme.mpandrade.mycoupon.provider

import android.content.Context

class UserPreference(context: Context) {

    companion object {

        const val PARAM_FIRE_BASE_AUTH_TOKEN = "fireBaseAuthToken"
        const val PARAM_PHONE_NUMBER = "phoneNumber"
    }

    private val preference = Preference(context)
    private val sharedPreference get() = preference.sharedPreference

    var fireBaseAuthToken: String?

        get() = sharedPreference.getString(PARAM_FIRE_BASE_AUTH_TOKEN, null)
        set(value) {
            putString(PARAM_FIRE_BASE_AUTH_TOKEN, value)
        }

    var phoneNumber: String?

        get() = sharedPreference.getString(PARAM_PHONE_NUMBER, null)
        set(value) {
            putString(PARAM_PHONE_NUMBER, value)
        }

    private fun putString(key: String, value: String?) {
        sharedPreference.edit().apply {
            this.putString(key, value)
            this.apply()
        }
    }

}