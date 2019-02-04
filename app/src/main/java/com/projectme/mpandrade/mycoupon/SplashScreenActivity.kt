package com.projectme.mpandrade.mycoupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.projectme.mpandrade.mycoupon.provider.UserPreference
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handle = Handler()

        val pref = UserPreference(this)

        handle.postDelayed({

            val cls = if (
                    pref.fireBaseAuthToken.isNullOrBlank() ||
                    Date(pref.fireBaseAuthTokenExpiration).before(Date())) {

                WelcomeActivity::class.java

            } else {

                MainActivity::class.java
            }

            val intent = Intent(this, cls)
            startActivity(intent)
            finish()

        }, 500L)
    }
}
