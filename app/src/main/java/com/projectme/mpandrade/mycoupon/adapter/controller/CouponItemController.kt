package com.projectme.mpandrade.mycoupon.adapter.controller

import android.content.Context
import android.graphics.drawable.*
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.projectme.mpandrade.mycoupon.R
import com.projectme.mpandrade.mycoupon.data.view.CouponData
import java.util.*
import androidx.core.util.Pair as PairX

class CouponItemController(view: View) {

    companion object {

        private const val IMAGE_WIDTH = 480
        private const val IMAGE_HEIGHT = 480

        val requestOptions = RequestOptions()
                .centerCrop()
                .error(R.drawable.background_shape_rectangle)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.HIGH)
                .override(IMAGE_WIDTH, IMAGE_HEIGHT)
    }

    private val companyNameTextView: TextView = view.findViewById(R.id.companyName)
    private val descriptionTextView: TextView = view.findViewById(R.id.description)
    private val statusTextView: TextView = view.findViewById(R.id.status)
    private val dueDateTextView: TextView = view.findViewById(R.id.dueDate)

    private val statusArea: LinearLayout = view.findViewById(R.id.statusArea)

    val favoriteIcon: ImageView = view.findViewById(R.id.favoriteIcon)
    val notFavoriteArea: ImageView = view.findViewById(R.id.notFavoriteIcon)

    val completeIcon: ImageView = view.findViewById(R.id.completeIcon)
    val receiveButton: Button = view.findViewById(R.id.receive)
    val favoriteArea: FrameLayout = view.findViewById(R.id.favoriteArea)
    val cardView: CardView = view.findViewById(R.id.cardCoupon)

    private val imageView: ImageView = view.findViewById(R.id.cardImage)

    val context get() : Context = cardView.context

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
            receiveButton.visibility = View.GONE
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

        if (!favorite) {
            favoriteIcon.visibility = View.GONE
            notFavoriteArea.visibility = View.VISIBLE
        } else {
            notFavoriteArea.visibility = View.GONE
            favoriteIcon.visibility = View.VISIBLE
        }
    }

    fun setDueDate(dueDate: Date?) {

        dueDateTextView.text =  if (dueDate == null) {

            context.getString(R.string.dueDateUndefined)
        } else {

            context.getString(R.string.dueDateDatePattern).format(dueDate)
        }

    }

    val transitionsElements get() : Array<PairX<View, String>> {

        return arrayOf<PairX<View, String>>(
                PairX(imageView, imageView.transitionName),
                PairX(cardView, cardView.transitionName)
        )
    }

}