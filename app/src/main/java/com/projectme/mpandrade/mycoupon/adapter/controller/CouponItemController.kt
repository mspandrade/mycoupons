package com.projectme.mpandrade.mycoupon.adapter.controller

import android.support.v4.util.Pair
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.CardView
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.projectme.mpandrade.mycoupon.R
import com.projectme.mpandrade.mycoupon.data.view.CouponData

class CouponItemController(view: View) {

    companion object {

        private const val IMAGE_WIDTH = 480
        private const val IMAGE_HEIGHT = 480

        val requestOptions = RequestOptions()
                .centerCrop()
                .error(R.drawable.shape_rectangle)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH)
                .override(IMAGE_WIDTH, IMAGE_HEIGHT)
    }

    private val companyNameTextView: TextView = view.findViewById(R.id.companyName)
    private val descriptionTextView: TextView = view.findViewById(R.id.description)
    private val statusTextView: TextView = view.findViewById(R.id.status)

    private val statusArea: LinearLayout = view.findViewById(R.id.statusArea)

    val favoriteIcon: ImageView = view.findViewById(R.id.favoriteIcon)
    val completeIcon: ImageView = view.findViewById(R.id.completeIcon)
    val receiveButton: Button = view.findViewById(R.id.receive)
    val favoriteArea: FrameLayout = view.findViewById(R.id.favoriteArea)
    val cardView: CardView = view.findViewById(R.id.cardCoupon)

    private val imageView: ImageView = view.findViewById(R.id.cardImage)

    private fun loadingSpinner() : CircularProgressDrawable {

        val circularProgressDrawable = CircularProgressDrawable(cardView.context)

        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        return circularProgressDrawable
    }

    fun setCompanyName(companyName: String) {
        companyNameTextView.text = companyName.toUpperCase()
    }

    fun setDescription(description: String) {
        descriptionTextView.text = description
    }

    fun setStatus(coupon: CouponData) {

        if (!coupon.isComplete) {

            statusTextView.text = statusTextView.context
                    .getString(R.string.statusContent, coupon.status, coupon.completeIn)

            completeIcon.visibility = View.GONE
        } else {

            statusArea.visibility = View.GONE
            receiveButton.visibility = View.VISIBLE
        }
    }

    fun setImage(imageUrl: String) {

        Glide.with(cardView.context)
                .applyDefaultRequestOptions(requestOptions.placeholder(loadingSpinner()))
                .asBitmap()
                .load(imageUrl)
                .into(imageView)
    }

    fun setIsFavorite(favorite: Boolean) {

        favoriteIcon.setImageResource( if (favorite) {

            R.drawable.ic_favorite

        } else {

            R.drawable.ic_notfavorite
        })
    }

    val transitionsElements get() : Array<Pair<View, String>> {

        return arrayOf<Pair<View, String>>(
                Pair(imageView, imageView.transitionName),
                Pair(cardView, cardView.transitionName)
        )
    }

}