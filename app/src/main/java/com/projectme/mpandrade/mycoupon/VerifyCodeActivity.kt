package com.projectme.mpandrade.mycoupon

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.projectme.mpandrade.mycoupon.factory.SpannableFactory
import kotlinx.android.synthetic.main.activity_verify_code.*
import java.util.concurrent.TimeUnit

class VerifyCodeActivity : AppCompatActivity(), TextWatcher {

    companion object {

        const val PARAM_PHONE_NUMBER = "paramPhoneNumber"
    }

    private var verificationId = ""
    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)

        phoneNumber = intent.getStringExtra(PARAM_PHONE_NUMBER)

        verifyTitle.text = getString(R.string.titleVerifyCode, phoneNumber)

        val questionWrongPhoneNumber = getString(R.string.questionWrongPhoneNumber)

        message.setText(
                SpannableFactory.instance(
                    getString( R.string.messageVerifyCode, phoneNumber, questionWrongPhoneNumber),
                    Pair(phoneNumber, StyleSpan(Typeface.DEFAULT_BOLD.style)),
                    Pair(questionWrongPhoneNumber, SpannableFactory.instanceClickableSpan {

                        Log.d("onClick", "TEST SPAN")
                    })
                ),
                TextView.BufferType.SPANNABLE
        )


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60L,
                TimeUnit.SECONDS,
                this,
                onVerificationStateChangedCallbacks
        )

        code.addTextChangedListener(this)
    }

    override fun afterTextChanged(editable: Editable) {

        if (editable.length >= 6) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    private val onVerificationStateChangedCallbacks get()

            : PhoneAuthProvider.OnVerificationStateChangedCallbacks  = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) { verificationCompleted(credential) }

            override fun onVerificationFailed(exception: FirebaseException) { verificationFailed(exception) }

            override fun onCodeSent(code: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) { codeSent(code) }
    }

    fun verificationCompleted(credential: PhoneAuthCredential) {

    }

    fun verificationFailed(exception: FirebaseException?) {

    }

    fun codeSent(verificationId: String) {
        this.verificationId = verificationId
    }

}
