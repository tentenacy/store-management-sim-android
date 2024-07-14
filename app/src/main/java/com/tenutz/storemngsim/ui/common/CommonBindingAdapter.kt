package com.tenutz.storemngsim.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.esafirm.imagepicker.model.Image
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.utils.ext.toPx


object CommonBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind:showImage")
    fun showImage(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(imageUrl)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(8.toPx.toInt()),
                    )
                )
            )
            .placeholder(R.drawable.placeholder_logo)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bind:showImage")
    fun showImage(imageView: ImageView, image: Image?) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(image?.uri)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(16.toPx.toInt()),
                    )
                )
            )
            .placeholder(R.drawable.placeholder)
            .into(imageView)
    }
}