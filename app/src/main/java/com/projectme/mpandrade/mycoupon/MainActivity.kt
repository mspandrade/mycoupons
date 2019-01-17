package com.projectme.mpandrade.mycoupon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.widget.Toast
import com.projectme.mpandrade.mycoupon.adapter.TabItemViewAdapter
import com.projectme.mpandrade.mycoupon.fragment.CompleteFragment
import com.projectme.mpandrade.mycoupon.fragment.CouponsFragment
import com.projectme.mpandrade.mycoupon.fragment.FavoriteFragment
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    companion object {

        const val cameraRequestCode = 50
    }

    private val hasCameraPermission get() = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setUpViewPager()
        setUpIconsTab()

        fab.setOnClickListener {

            if (!hasCameraPermission) {
                ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.CAMERA), cameraRequestCode)
            } else {
                openQRCode()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == cameraRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openQRCode()
        } else {
            Toast.makeText(this, getString(R.string.qrCodeCameraPermissionError), Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpViewPager() {

        viewPager.offscreenPageLimit = 3

        val adapter = TabItemViewAdapter(supportFragmentManager)

        adapter.add(CouponsFragment(), getString(R.string.coupons))
        adapter.add(CompleteFragment(), getString(R.string.complete))
        adapter.add(FavoriteFragment(), "")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setUpIconsTab() {
        tabLayout.getTabAt(2)?.setIcon(R.drawable.tab_selector_favorite)
    }

    private fun openQRCode() {
        val intent = Intent(this, CouponQRCodeReaderActivity::class.java)
        startActivity(intent)
    }
}
