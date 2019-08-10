package com.reclancer.controller.launch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.reclancer.R
import com.reclancer.common.ONE_SECOND
import com.reclancer.common.startActivityClearStack
import com.reclancer.controller.login.LoginActivity
import com.reclancer.utils.isUserLoggedIn
import io.reactivex.observers.DisposableCompletableObserver


abstract class LauncherActivity : AppCompatActivity() {

    /**
     * The app name that will be displayed on the splash screen
     */
    abstract fun appName(): String

    /**
     * The Activity to route to once the user is logged in
     */
    abstract val contentActivity: Class<*>

    /**
     * Implementing apps are responsible for initializing their service locator and
     * reporting back to the [observer]
     */
    abstract fun initializeServiceLocator(observer: DisposableCompletableObserver)


    /**
     * Optional Intent extras bundle support for passing to the contentActivity.
     */
    var contentActivityExtras: Bundle? = null

    /**
     * Minimum time the splash screen should be shown
     */
    private val splashThreshold = ONE_SECOND * 3

    protected open fun initializeLayout(): Boolean = false

    /**
     * Sleep until the screen has been visible for [splashThreshold].
     * Overridable for custom handling of
     * @param sessionValid true if the user is logged in and has a valid session, false if the user
     *                     is not logged in.
     */
    open fun waitOnSplash(millis: Long) {
        Thread.sleep(millis)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!initializeLayout()) {
            setContentView(R.layout.activity_launcher)
        }

        initializeServiceLocator(serviceLocatorObservable())
    }

    /**
     * Routes the user on success, finishes the app on an error
     */
    private fun serviceLocatorObservable() = object : DisposableCompletableObserver() {

        override fun onComplete() {
            //userName = "Hello"
            //userEmailId = "Hello@123"

            if (isUserLoggedIn()) {
                routeUser()
            } else {
                LoginActivity.startLoginActivity(this@LauncherActivity)
            }
        }

        override fun onError(e: Throwable) {
            displayNetworkError()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LoginActivity.LOGIN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                startActivityClearStack(contentActivity, extras = contentActivityExtras)
            }
            finish()
        }
    }

    /**
     * Display network error message
     */
    private fun displayNetworkError() {
        //If no connection or we can't find the services, we can't do anything else. Kill the app and tell the user
        //longToast(R.string.error_service_locator)
        finish()
    }

    //this method should be private, but for the time being keeping it public
    fun routeUser() {
        startActivityClearStack(contentActivity, extras = contentActivityExtras)
        this@LauncherActivity.finish()
    }

}