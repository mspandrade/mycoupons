package com.projectme.mpandrade.mycoupon

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import kotlinx.android.synthetic.main.activity_login.*
class LoginActivity : AppCompatActivity() {

    private var ddiValue = ""
    private var currentLocale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ddiValue = getString(R.string.ddiMask, getString(R.string.defaultDDI))

        ddi.text = ddiValue

        phoneNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher(currentLocale.country))
        phoneNumber.requestFocus()

        next.setOnClickListener { onNextClicked() }
    }

    private fun onNextClicked() {

        var actionOk: () -> Unit = {}

        val alert = AlertDialog.Builder(this)
                .setCancelable(true)

        val phoneNumberFormatted = phoneNumber.text.replace(Regex("\\s|-"), "")

        if (phoneNumber.text.isNullOrEmpty()) {

            alert.setMessage(R.string.alertMessageEmptyPhoneNumber)

        } else if (phoneNumberFormatted.length < 11) {

            alert.setMessage(
                    getString(R.string.alertMessageShortPhoneNumber,
                            currentLocale.displayCountry)
            )

        } else if (!PhoneNumberUtils.isGlobalPhoneNumber("$ddiValue$phoneNumberFormatted")) {

            alert.setMessage(getString(
                    R.string.alertMessageInvalidPhoneNumber,
                    "$ddiValue ${phoneNumber.text}",
                    currentLocale.displayCountry
            ))

        } else {

            alert.setNeutralButton(R.string.alertButtonEdit) { _, _ -> }

            alert.setMessage(getString(R.string.alertMessageSuccessPhoneNumber, phoneNumber.text))

            actionOk = {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        alert.setPositiveButton(android.R.string.ok) { _, _ -> actionOk.invoke() }

        val color = ContextCompat.getColor(this, R.color.colorPrimary)
        val dialog = alert.show()

        dialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(color)
        dialog?.getButton(AlertDialog.BUTTON_NEUTRAL)?.setTextColor(color)
    }

}

