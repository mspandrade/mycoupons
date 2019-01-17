package com.projectme.mpandrade.mycoupon.adapter.controller

import android.content.Context
import android.graphics.drawable.*
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.CardView
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
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

    private val context get() = cardView.context

    private fun loadingImage() : Drawable {

        val shape = ContextCompat.getDrawable(context, R.drawable.background_image_animated_vector)!!

        (shape as Animatable).start()

        return shape
    }

    fun setCompanyName(companyName: String) {
        companyNameTextView.text = companyName.toUpperCase()
    }

    fun setDescription(description: String) {
        descriptionTextView.text = description
    }

    fun setStatus(coupon: CouponData) {

        if (!coupon.isComplete) {

            statusTextView.text = context.getString(R.string.statusContent, coupon.status, coupon.completeIn)

            completeIcon.visibility = View.GONE
        } else {

            statusArea.visibility = View.GONE
            receiveButton.visibility = View.VISIBLE
        }
    }

    fun setImage(imageUrl: String) {

        Glide.with(context)
                .applyDefaultRequestOptions(requestOptions.placeholder(loadingImage()))
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