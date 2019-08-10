package com.reclancer.controller.base

import android.util.Log
import com.reclancer.common.ActionListener

abstract class BaseActivity: RootActivityLayout(), ActionListener {

    /**
     * Prevent onBackPressed override in child activities
     */
    final override fun onBackPressed() {
            handleBackPressed()
    }

    open fun handleBackPressed() {}

    protected fun superOnBackPressed() {
        super.onBackPressed()
    }

    override fun onAction(action: String, data: Any?) {
        when (action) {
            ACTION_SHOW_NETWORK_ERROR -> showNetworkError()
            else -> if (!onPerformAction(action, data)) Log.d(TAG, "Unexpected action: $action")
        }
    }

    /**
     * Override to handle fragment specific actions
     * @param action The action to take
     * @param data optional data to perform the action with
     */
    open fun onPerformAction(action: String, data: Any?) = false


    companion object {
        private val TAG = BaseActivity::class.java.simpleName
        val ACTION_SHOW_NETWORK_ERROR = BaseActivity::class.java.simpleName + ".showNetworkError"
    }
}