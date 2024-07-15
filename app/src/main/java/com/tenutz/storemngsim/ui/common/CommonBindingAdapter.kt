package com.tenutz.storemngsim.ui.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.esafirm.imagepicker.model.Image
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.utils.HourTextWatcher
import com.tenutz.storemngsim.utils.MinuteTextWatcher
import com.tenutz.storemngsim.utils.ext.toPx
import kotlin.math.min


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

    @JvmStatic
    @BindingAdapter("bind:packagingCode")
    fun setPackagingCode(textView: TextView, code: String?) {
        code?.let {
            textView.text = when (it) {
                "02" -> {
                    "매장 전용"
                }
                "03" -> {
                    "포장 전용"
                }
                else -> {
                    "매장,포장"
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bind:highlightTypeCode")
    fun setHighlightTypeCode(textView: TextView, code: String?) {
        code?.let {
            textView.text = when (it) {
                "01" -> {
                    "신규"
                }
                "02" -> {
                    "추천"
                }
                "03" -> {
                    "인기"
                }
                "04" -> {
                    "행사"
                }
                else -> {
                    "미사용"
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bind:weekDayIn")
    fun weekDayIn(checkBox: CheckBox, weekDaysText: String?) {
        weekDaysText?.split(",")?.takeIf { it.isNotEmpty() }?.let { weekDays ->
            checkBox.isChecked = weekDays.any { it == checkBox.text.toString() }
        }
    }

    @JvmStatic
    @BindingAdapter("bind:timeFormat")
    fun setInputFormat(editText: EditText, format: String?) {
        when (format) {
            "hour" -> {
                editText.addTextChangedListener(HourTextWatcher(editText))
            }
            "minute" -> {
                editText.addTextChangedListener(MinuteTextWatcher(editText))
            }
        }
    }
}