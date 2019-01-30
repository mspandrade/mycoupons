package com.projectme.mpandrade.mycoupon.factory

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

object SpannableFactory {

    fun instance(text: String, vararg spans: Pair<String, Any>): SpannableStringBuilder {

        val spannable = SpannableStringBuilder(text)

        spans.forEach {

            val index = text.lastIndexOf(it.first)

            if (index >= 0 ) {

                spannable.setSpan(it.second, index, index + it.first.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return spannable
    }

    fun instanceClickableSpan(onClick: () -> Unit) : ClickableSpan = object: ClickableSpan() {

        override fun onClick(widget: View) {
            onClick.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

}