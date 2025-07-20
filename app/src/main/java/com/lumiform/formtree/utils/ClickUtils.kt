package com.lumiform.formtree.utils

import android.util.Log
import android.view.View

/**
 * @created 20/07/2025 - 11:20 AM
 * @project FormTree
 * @author adell
 */
object ClickUtils {

    private const val TAG = "ClickUtils"

    private const val DEBOUNCING_TAG = -7
    private const val DEBOUNCING_DEFAULT_VALUE = 350L

    fun applySingleDebouncing(view: View, listener: View.OnClickListener) {
        applySingleDebouncing(arrayOf(view), DEBOUNCING_DEFAULT_VALUE, listener)
    }

    fun applySingleDebouncing(views: Array<View>, listener: View.OnClickListener) {
        applySingleDebouncing(views, DEBOUNCING_DEFAULT_VALUE, listener)
    }

    fun applySingleDebouncing(
        views: Array<View>,
        duration: Long,
        listener: View.OnClickListener
    ) {
        applyDebouncing(views, isGlobal = false, duration, listener)
    }

    private fun applyDebouncing(
        views: Array<View>?,
        isGlobal: Boolean,
        duration: Long,
        listener: View.OnClickListener?
    ) {
        if (views.isNullOrEmpty() || listener == null) return

        for (view in views) {
            view.setOnClickListener(object : OnDebouncingClickListener(isGlobal, duration) {
                override fun onDebouncingClick(v: View) {
                    Log.d(TAG, "on view click listener execute")
                    listener.onClick(v)
                    ClickManager.getInstance().startCounting(duration)
                }
            })
        }
    }

    abstract class OnDebouncingClickListener(
        private val isGlobal: Boolean,
        private val duration: Long
    ) : View.OnClickListener {

        companion object {
            private var enabled = true
            private val enableAgain = Runnable { enabled = true }

            private fun isValid(view: View, duration: Long): Boolean {
                val curTime = System.currentTimeMillis()
                val tag = view.getTag(DEBOUNCING_TAG)
                if (tag !is Long) {
                    view.setTag(DEBOUNCING_TAG, curTime)
                    return true
                }
                val preTime = tag
                return if (curTime - preTime <= duration) {
                    false
                } else {
                    view.setTag(DEBOUNCING_TAG, curTime)
                    true
                }
            }
        }

        override fun onClick(v: View) {
            if (ClickManager.getInstance().isButtonClicked()) return

            Log.d(TAG, "on view clicked")
            if (isGlobal) {
                if (enabled) {
                    enabled = false
                    v.postDelayed(enableAgain, duration)
                    onDebouncingClick(v)
                }
            } else {
                if (isValid(v, duration)) {
                    onDebouncingClick(v)
                }
            }
        }

        abstract fun onDebouncingClick(v: View)
    }
}
