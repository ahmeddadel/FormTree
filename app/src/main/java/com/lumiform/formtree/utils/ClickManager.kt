package com.lumiform.formtree.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @created 20/07/2025 - 11:21 AM
 * @project FormTree
 * @author adell
 */
class ClickManager private constructor() {

    private var isButtonClicked = false
    private val defaultDurationValue = 350L

    fun isButtonClicked(): Boolean = isButtonClicked

    fun startCounting() {
        startCounting(defaultDurationValue)
    }

    fun startCounting(duration: Long) {
        isButtonClicked = true
        CoroutineScope(Dispatchers.Main).launch {
            delay(duration)
            isButtonClicked = false
        }
    }

    companion object {
        @Volatile
        private var instance: ClickManager? = null

        fun getInstance(): ClickManager {
            return instance ?: synchronized(this@Companion) {
                instance ?: ClickManager().also { instance = it }
            }
        }
    }
}
