package com.projectme.mpandrade.mycoupon

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import kotlinx.android.synthetic.main.activity_coupon_qrcode_reader.*
import kotlinx.android.synthetic.main.cell_loading.*

class CouponQRCodeReaderActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener {

    private var qrDecoderView: QRCodeReaderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_qrcode_reader)

        title = getString(R.string.readCoupon)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val handle = Handler()

        handle.post {

            layoutInflater.inflate(R.layout.cell_qrcode_reader, qrCodeArea)

            initScannerBarAnimation()

            qrDecoderView = findViewById(R.id.qrDecoderView)

            qrDecoderView?.setOnQRCodeReadListener(this)
            qrDecoderView?.setQRDecodingEnabled(true)
            qrDecoderView?.setAutofocusInterval(2000L)
            qrDecoderView?.setTorchEnabled(true)
            qrDecoderView?.setBackCamera()

            loading.visibility = View.GONE
        }
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
        qrDecoderView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        qrDecoderView?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrDecoderView?.stopCamera()
    }

    private fun initScannerBarAnimation() {

        qrCodeAnimationArea.visibility = View.VISIBLE

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

