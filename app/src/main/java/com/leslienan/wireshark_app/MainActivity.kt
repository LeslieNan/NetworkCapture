package com.leslienan.wireshark_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.leslienan.wireshark.NetLogModel
import com.leslienan.wireshark.WiresharkUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val model = NetLogModel(
                "GET",
                "https://api.222.com", "id=100", "",
                "", "responseHeader", "",
                300, System.currentTimeMillis()
            )
            WiresharkUtil.addNetLog(model)
        }
    }
}