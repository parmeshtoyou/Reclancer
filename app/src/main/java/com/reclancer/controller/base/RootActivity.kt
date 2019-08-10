package com.reclancer.controller.base

import android.support.v7.app.AppCompatActivity


abstract class RootActivity : AppCompatActivity() {

    /** Pending Changes **/
    private val runOnResume = ArrayList<Runnable>()
    protected var resumedState = false

    /**
     * Subclasses should call this method whenever changes affect the views, primarily from data loading.
     * Supports data loading actions which complete after the app is backgrounded and changes need to be made to views.
     */
    fun notifyPendingChange(runnable: Runnable) {
        if (resumedState) {
            runOnUiThread(runnable)
        } else {
            runOnResume.add(runnable)
        }
    }

    /* Run any pending runables upon resuming */
    private fun viewUpdateOnResume() {
        if (runOnResume.isNotEmpty()) {
            runOnResume.forEach {
                it.run()
            }
            runOnResume.clear()
        }
    }

}