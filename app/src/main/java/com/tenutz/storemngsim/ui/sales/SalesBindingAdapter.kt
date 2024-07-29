package com.tenutz.storemngsim.ui.sales

import android.widget.TextView
import androidx.databinding.BindingAdapter

object SalesBindingAdapter {

    @BindingAdapter("bind:paymentType")
    @JvmStatic
    fun setPaymentType(textView: TextView, paymentType: String?) {
        when(paymentType) {
            "01" -> {
                textView.text = "현금결제"
            }
            "02" -> {
                textView.text = "카드결제"
            }
            "03" -> {
                textView.text = "쿠폰"
            }
//            "04" -> {
//                textView.text = "멤버쉽"
//            }
        }
    }


    @BindingAdapter("bind:approvalType")
    @JvmStatic
    fun setApprovalType(textView: TextView, approvalType: String?) {
        when(approvalType) {
            "N" -> {
                textView.text = "승인"
            }
            "C" -> {
                textView.text = "취소"
            }
        }
    }


    @BindingAdapter("bind:orderType")
    @JvmStatic
    fun setOrderType(textView: TextView, orderType: String?) {
        when(orderType) {
            "05" -> {
                textView.text = "내점"
            }
            "06" -> {
                textView.text = "포장"
            }
//            "08" -> {
//                textView.text = "태블릿"
//            }
        }
    }

}