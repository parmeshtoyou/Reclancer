package com.reclancer.controller.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.reclancer.R
import com.reclancer.common.restartApp
import com.reclancer.common.showLongSnackBar
import kotlinx.android.synthetic.main.root_activity_layout.*

abstract class RootActivityLayout : RootActivity() {

    open var menuItemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root_activity_layout)
        savedInstanceState?.let {
            restartApp()
        }
    }

    /**
     * Standard mechanism for displaying a [fragment]]
     */
    open fun showFragment(fragment: Fragment, addToBackStack: Boolean = false, showTutorialInMenu: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

    /**
     * Override the hamburger Icon to display [res]
     */
    fun setHomeIcon(res: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(res)
    }

    fun showNetworkError() {
        showSnackBar(coordinatorLayout, R.string.error_network)
    }

    fun showProgress() {
        //Todo: show progress bar when initiating network call
    }

    fun hideProgress() {
        notifyPendingChange(Runnable {
            fragmentContainer.postDelayed({
                //Todo: hide progress bar here
            }, PROGRESS_DIALOG_DISMISS_DELAY)
        })
    }

    companion object {

        private const val PROGRESS_DIALOG_DISMISS_DELAY = 100L

        /**
         * Boolean to prevent consecutive loading of progress bar in short intervals
         * which might cause Fragment already added exception
         */
        var progressShown = false

        fun showSnackBar(view: View, resId: Int) {
            view.context.showLongSnackBar(view, resId)
        }
    }
}