package com.reclancer.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import kotlin.system.exitProcess

const val TAG = "ContextUtil"

const val EMPTY_STRING = ""

/**
 * Extension to kill all activities and launch the app from scratch
 */
fun Context.restartApp() {
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent)
    exitProcess(0)
}

fun Activity.startActivity(cls: Class<*>, options: Bundle? = null, finish: Boolean = false, extras: Bundle? = null) {
    val intent = Intent(this, cls)
    if (extras != null) {
        intent.putExtras(extras)
    }
    startActivity(intent, options)
    if (finish) {
        finish()
    }
}

fun Activity.startActivityClearStack(cls: Class<*>, options: Bundle? = null, extras: Bundle? = null) {
    startActivity(Intent(this, cls).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        if (extras != null) {
            putExtras(extras)
        }
    }, options)
}

fun Activity.startActivityForResult(cls: Class<*>, requestCode: Int, options: Bundle? = null, extras: Bundle? = null) {
    val intent = Intent(this, cls)
    if (extras != null) {
        intent.putExtras(extras)
    }
    startActivityForResult(intent, requestCode, options)
}

fun Activity.finishResultOK() {
    setResult(Activity.RESULT_OK)
    finish()
}

fun Activity.dismissKeyboard() {
    if (currentFocus != null) {
        dismissKeyboard(currentFocus)
    }
}

fun Activity.dismissKeyboard(view: View) {
    try {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: Exception) {
        Log.d(TAG, "Exception dismissing keyboard", e)
    }
}

fun Activity.inflate(resource: Int, root: ViewGroup? = null) = this.layoutInflater.inflate(resource, root)

fun Context.inflate(resource: Int, root: ViewGroup? = null) = LayoutInflater.from(this).inflate(resource, root)

fun Context.toast(text: String, length: Int) {
    val toast = Toast.makeText(this, text, length)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun Context.toast(resId: Int, length: Int) {
    val toast = Toast.makeText(this, resId, length)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun Context.shortToast(text: String) {
    toast(text, Toast.LENGTH_SHORT)
}

fun Context.shortToast(resId: Int) {
    toast(resId, Toast.LENGTH_SHORT)
}

fun Context.longToast(text: String) {
    toast(text, Toast.LENGTH_LONG)
}

fun Context.longToast(resId: Int) {
    toast(resId, Toast.LENGTH_LONG)
}

fun Context.showShortSnackBar(view: View, resId: Int) {
    Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show()
}

fun Context.showLongSnackBar(view: View, resId: Int) {
    Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show()
}

fun Context.isOnline(): Boolean {
    //val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //val netInfo = cm.activeNetworkInfo
    //return netInfo != null && netInfo.isConnectedOrConnecting
    return true
}

