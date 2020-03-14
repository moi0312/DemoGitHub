package com.edlo.demogithub.util

import android.util.Log
import com.edlo.demogithub.BuildConfig

class Log {
    companion object {
        const val DEFAULT_TAG = BuildConfig.APPLICATION_ID
        val PRINT_LOG = BuildConfig.PRINT_LOG

        fun v(tag: String, msg: String) {
            if(PRINT_LOG) Log.v(tag, msg)
        }
        fun v(msg: String) { v(DEFAULT_TAG, msg) }

        fun i(tag: String, msg: String) {
            if(PRINT_LOG) Log.i(tag, msg)
        }
        fun i(msg: String) { i(DEFAULT_TAG, msg) }

        fun d(tag: String, msg: String) {
            if(PRINT_LOG) Log.d(tag, msg)
        }
        fun d(msg: String) { d(DEFAULT_TAG, msg) }

        fun w(tag: String, msg: String) {
            if(PRINT_LOG) Log.w(tag, msg)
        }
        fun w(msg: String) { w(DEFAULT_TAG, msg) }

        fun e(tag: String, msg: String) {
            Log.e(tag, msg)
        }
        fun e(msg: String) { e(DEFAULT_TAG, msg) }
    }
}
