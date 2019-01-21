package com.projectme.mpandrade.mycoupon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.projectme.mpandrade.mycoupon.adapter.TabItemViewAdapter
import com.projectme.mpandrade.mycoupon.data.service.CouponService
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import com.projectme.mpandrade.mycoupon.fragment.CompleteFragment
import com.projectme.mpandrade.mycoupon.fragment.CouponsFragment
import com.projectme.mpandrade.mycoupon.fragment.FavoriteFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


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
        initCoupons()
    }

    private fun initCoupons() {

        val coupons = arrayOf(
                CouponData(
                        1,
                        "Habbibs",
                        "Na compra de 10 esfirras ganha-se 1",
                        10, 15,
                        null,
                        "http://propmark.com.br/static/upload/legacy/thumbs/2014/gtahabibs600.jpg",
                        true),

                CouponData(
                        2,
                        "McDonald's",
                        "Na compra de 1 lanche ganha-se outro",
                        0, 1,
                        Date(),
                        "https://geekpublicitario.com.br/wp-content/uploads/2016/03/promocao-big-tasty-2-em-1-mcdonalds-blog-geek-publicitario.jpg",
                        false),

                CouponData(
                        3,
                        "Divino Fogão",
                        "A cada 5 refeições ganha-se um brinde",
                        5, 5,
                        Date(),
                        "https://anrbrasil.org.br/wp-content/uploads/2018/02/Post_03b_Ferdinand_600x600px.png",
                        true),

                CouponData(
                        4,
                        "Burguer King",
                        "Na compra de 1 lanche ganha-se outro",
                        0, 1,
                        Date(),
                        "https://static.pelando.com.br/live/threads/thread_full_screen/default/248242_1.jpg",
                        false),

                CouponData(
                        5,
                        "Pizza Hut",
                        "Na compra de duas pizzas ganha-se um refrigerante",
                        0, 2,
                        Date(),
                        "https://i.pinimg.com/originals/cf/de/ab/cfdeabd2b571016773f3c68edfbb8262.jpg",
                        false),

                CouponData(
                        6,
                        "KFC",
                        "Na compra de 1 lanche ganha-se outro",
                        0, 1,
                        Date(),
                        "http://guiadopreço.com/wp-content/uploads/2017/01/kfc--e1483623133166.jpg",
                        true),

                CouponData(
                        6,
                        "SUBWAY",
                        "Na compra de 1 lanche ganha-se outro",
                        0, 1,
                        Date(),
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaOHg66n04wCfwuF4jLGneerMWxXDA7VBkqQ0lXXhhN15vEAaNmA",
                        false)
        )
        val service = CouponService(this)
        service.cleanStorage()
        service.insertAll(*coupons)
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
