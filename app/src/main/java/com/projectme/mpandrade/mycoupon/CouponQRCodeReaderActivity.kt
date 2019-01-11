package com.projectme.mpandrade.mycoupon

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateDecelerateInterpolator
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import kotlinx.android.synthetic.main.activity_coupon_qrcode_reader.*

class CouponQRCodeReaderActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_qrcode_reader)

        title = getString(R.string.readCoupon)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initScannerBarAnimation()

        qrDecoderView.setOnQRCodeReadListener(this)
        qrDecoderView.setQRDecodingEnabled(true)
        qrDecoderView.setAutofocusInterval(2000L)
        qrDecoderView.setTorchEnabled(true)
        qrDecoderView.setBackCamera()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        finish()
    }

    override fun onResume() {
        super.onResume()
        qrDecoderView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        qrDecoderView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrDecoderView.stopCamera()
    }

    private fun initScannerBarAnimation() {

        val vto = scannerLayout.viewTreeObserver
        vto.addOnGlobalLayoutListener{

            val destination = scannerBar.y + scannerLayout.height - scannerBar.height

            val animator = ObjectAnimator.ofFloat(scannerBar, "translationY", scannerBar.y, destination)

            animator.repeatMode = ValueAnimator.REVERSE
            animator.repeatCount = ValueAnimator.INFINITE
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.duration = 1000
            animator.start()
        }
    }
}

