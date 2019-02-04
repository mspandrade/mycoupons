package com.projectme.mpandrade.mycoupon

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.projectme.mpandrade.mycoupon.factory.SpannableFactory
import com.projectme.mpandrade.mycoupon.provider.UserPreference
import kotlinx.android.synthetic.main.activity_verify_code.*
import java.util.concurrent.TimeUnit
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.cell_loading.*
import java.util.*


class VerifyCodeActivity : AppCompatActivity(), TextWatcher {

    companion object {

        const val PARAM_PHONE_NUMBER = "paramPhoneNumber"
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var credential: PhoneAuthCredential? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
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

            loading.visibility = View.VISIBLE

            val credential = this.credential ?: PhoneAuthProvider.getCredential(verificationId, code.text.toString())
            signInUser(credential)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    private val onVerificationStateChangedCallbacks get()

            : PhoneAuthProvider.OnVerificationStateChangedCallbacks  = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) { verificationCompleted(credential) }

            override fun onVerificationFailed(exception: FirebaseException) { verificationFailed(exception) }

            override fun onCodeSent(code: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) { codeSent(code, forceResendingToken) }
    }

    fun verificationCompleted(credential: PhoneAuthCredential) {
        this.credential = credential
        code.setText(credential.smsCode)
    }

    fun verificationFailed(exception: FirebaseException?) {

        Log.e("e", exception?.message)

        loading.visibility = View.GONE
    }

    fun codeSent(verificationId: String, resendToken: PhoneAuthProvider.ForceResendingToken) {

        loading.visibility = View.GONE

        this.verificationId = verificationId
        this.resendToken = resendToken
    }

    private fun hideKeyboard() {

        val service = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager

        val view = currentFocus ?: View(this)

        service?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun signInUser(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential).addOnCompleteListener(this) {

            loading.visibility = View.GONE

            if (it.isSuccessful) {


                it.result?.user?.getIdToken(true)?.addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val userPreferences = UserPreference(this)

                        hideKeyboard()

                        userPreferences.fireBaseAuthToken = task.result?.token
                        userPreferences.fireBaseAuthTokenExpiration = Date().time + (task.result?.expirationTimestamp ?: 0)
                        userPreferences.phoneNumber = it.result?.user?.phoneNumber

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            } else {

                Log.w("VerifyCode", "signInWithCredential:failure", it.exception)

                if (it.exception is FirebaseAuthInvalidCredentialsException) {

                }
            }
        }
    }

}
