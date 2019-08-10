package com.reclancer.controller.launch

import com.reclancer.controller.home.HomeActivity
import com.reclancer.R
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import java.util.concurrent.TimeUnit

class LaunchActivity : LauncherActivity() {

    override fun appName() = getString(R.string.app_name)
    private val compositeDisposable = CompositeDisposable()

    override fun initializeServiceLocator(observer: DisposableCompletableObserver) {
        compositeDisposable.add(Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .subscribeWith(observer))
    }

    override var contentActivity = HomeActivity::class.java

    override fun initializeLayout(): Boolean {
        setContentView(R.layout.app_launch_view)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}