package com.projectme.mpandrade.mycoupon

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_welcome.*
import android.content.Intent
import android.net.Uri


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

        val span = SpannableString(messageUsagePolicyString)

        setLink(span, privacyPolicyString) {

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_PRIVACY_POLICY))
            startActivity(browserIntent)
        }

        setLink(span, serviceTermsString) {

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_SERVICE_TERMS))
            startActivity(browserIntent)
        }

        messageUsagePolicy.movementMethod = LinkMovementMethod.getInstance()
        messageUsagePolicy.setText(span, TextView.BufferType.SPANNABLE)
        messageUsagePolicy.highlightColor = Color.TRANSPARENT


        next.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setLink(span: SpannableString, text: String, onClick: () -> Unit) {

        val index = span.lastIndexOf(text)

        span.setSpan(
                instanceClickableSpan(onClick),
                index,
                index + text.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun instanceClickableSpan(onClick: () -> Unit) : ClickableSpan = object: ClickableSpan() {

        override fun onClick(widget: View) {
            onClick.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

}
