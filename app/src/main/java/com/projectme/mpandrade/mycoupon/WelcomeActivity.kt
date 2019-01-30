package com.projectme.mpandrade.mycoupon

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_welcome.*
import android.content.Intent
import android.net.Uri
import com.projectme.mpandrade.mycoupon.factory.SpannableFactory


class WelcomeActivity : AppCompatActivity() {

    companion object {

        const val URL_PRIVACY_POLICY = "https://www.whatsapp.com/legal/#privacy-policy"
        const val URL_SERVICE_TERMS = "https://www.whatsapp.com/legal/#terms-of-service"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val privacyPolicyString = getString(R.string.privacyPolicy)
        val serviceTermsString = getString(R.string.serviceTerms)

        val messageUsagePolicyString = getString(

                R.string.messageUsagePolicy,
                privacyPolicyString,
                serviceTermsString
        )

        val span = SpannableFactory.instance(

                messageUsagePolicyString,

                Pair(privacyPolicyString, SpannableFactory.instanceClickableSpan {

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_PRIVACY_POLICY))
                    startActivity(browserIntent)
                }),

                Pair(serviceTermsString, SpannableFactory.instanceClickableSpan {

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_SERVICE_TERMS))
                    startActivity(browserIntent)
                })
        )

        messageUsagePolicy.movementMethod = LinkMovementMethod.getInstance()
        messageUsagePolicy.setText(span, TextView.BufferType.SPANNABLE)
        messageUsagePolicy.highlightColor = Color.TRANSPARENT

        next.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
