package com.tenutz.storemngsim.application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.tenutz.storemngsim.databinding.ActivityAddrSearchBinding

class AddressSearchActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddrSearchBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddrSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // javascript 를 사용할 수 있게 셋팅.
        binding.webAddrSearch.settings.javaScriptEnabled = true

        // javascript 의 window.open 허용.
        binding.webAddrSearch.settings.javaScriptCanOpenWindowsAutomatically = true

        binding.webAddrSearch.webChromeClient = WebChromeClient()

        binding.webAddrSearch.addJavascriptInterface(AndroidBridge(), "OMS")
        binding.webAddrSearch.loadUrl("http://mbiz.bizsalad.net/addr.jsp")
    }

    private inner class AndroidBridge {
        @JavascriptInterface
        fun setAddress(param1: String, param2: String) {
            val extra = Bundle()
            extra.putString("address", param1)
            extra.putString("roadAddress", param2)
            val intent = Intent()
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}