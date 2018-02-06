package com.gaohui.customprogressbar

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val EMPTY_MSG = 0x001
    }

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //获得刚才发送的Message对象，然后在这里进行UI操作
            progressBar01.progress++
            progressBar02.progress++
            progressBar03.progress++
            progressBar04.progress++
            progressBar05.progress++
            progressBar06.progress++
            progressBar07.progress++
            if(progressBar01.progress >= 100) {
                this.removeMessages(EMPTY_MSG)

            }
            this.sendEmptyMessageDelayed(EMPTY_MSG, 100)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mHandler.sendEmptyMessage(EMPTY_MSG)

    }

}
