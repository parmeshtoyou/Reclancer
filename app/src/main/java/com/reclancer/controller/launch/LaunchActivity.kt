package com.reclancer.controller.launch

import com.reclancer.controller.home.HomeActivity
import com.reclancer.R
import io.reactivex.observers.DisposableCompletableObserver

class LaunchActivity: LauncherActivity() {

    override fun appName() = getString(R.string.app_name)

    override fun initializeServiceLocator(observer: DisposableCompletableObserver) {
    }

    override var contentActivity = HomeActivity::class.java

    override fun initializeLayout(): Boolean {
        setContentView(R.layout.app_launch_view)
        return true
    }
}