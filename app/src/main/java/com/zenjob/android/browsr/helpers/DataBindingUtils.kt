package com.zenjob.android.browsr.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.zenjob.android.browsr.Constants.WebServiceConstants

object DataBindingUtils {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.setImageUrl(poserPath: String?) {

        Picasso.get().load(WebServiceConstants.POSTER_PATH + poserPath).into(this)
    }
}