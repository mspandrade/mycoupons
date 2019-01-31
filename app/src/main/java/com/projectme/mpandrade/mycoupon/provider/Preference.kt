package com.projectme.mpandrade.mycoupon.provider

import android.content.Context
import android.content.SharedPreferences
import com.projectme.mpandrade.mycoupon.R

class Preference(private val context: Context) {

    private var mEditMode: SharedPreferences.Editor? = null
    private var mReadMode: SharedPreferences? = null

    val sharedPreference get(): SharedPreferences {

        if (mReadMode == null) {

            mReadMode = context.getSharedPreferences(
                    context.getString(R.string.preferenceFileKey),
                    Context.MODE_PRIVATE
            )
        }
        return mReadMode!!
    }

}