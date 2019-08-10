package com.reclancer.controller.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.MenuInflater
import android.view.Menu
import com.reclancer.common.ActionListener

abstract class BaseFragment: Fragment() {

    companion object {
        const val NO_OPTIONS_MENU = -1
    }

    /**
     * The layout resource to inflate into this fragment
     */
    abstract fun layoutResource(): Int

    /**
     * The menu resource file. Do not override if there are no options in the action bar
     */
    open fun optionsMenuResource() = NO_OPTIONS_MENU

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(optionsMenuResource() != NO_OPTIONS_MENU)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutResource(), container, false)

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(optionsMenuResource(), menu)
    }

    override fun onResume() {
        super.onResume()
        viewUpdateOnResume()
    }

    fun showNetworkError() {
        (activity as? BaseActivity)?.showNetworkError()
    }

    fun showProgress() {
        (activity as? BaseActivity)?.showProgress()
    }

    fun hideProgress() {
        (activity as? BaseActivity)?.let { it.notifyPendingChange(Runnable {
            it.hideProgress()
        })
        }
    }

    private fun viewUpdateOnResume() {
        if (needToUpdateViews) {
            updateViews()
            needToUpdateViews = false
        }
    }

    private var needToUpdateViews = false

    private val updateViewsRunnable = Runnable {
        updateViews()
    }

    /**
    * Subclasses should call this method whenever the underlying data model changes (i.e., any time anything changes
    * that could affect the views).
    */
    fun notifyModelChanged() {
        if (isResumed) {
            activity?.runOnUiThread(updateViewsRunnable)
        } else {
            needToUpdateViews = true
        }
    }

    /**
     * Subclasses should override this method to refresh the views to reflect the current state of the data model.  This
     * method will be called whenever {@link #notifyModelChanged()} is called while the activity is resumed,
     * and in onResume if {@link #notifyModelChanged()} was called while the activity was paused.
     */
    open fun updateViews() {
    }

    var actionListener: ActionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ActionListener) {
            actionListener = context
        } else {
            throw ActionListener.ActionListenerException("Activity is expected to implement ActionListener")
        }
    }

    override fun onDetach() {
        actionListener = null
        super.onDetach()
    }

    /**
     * Wrap the action listener when passing through to an adapter. Never hold a reference
     * to the actual action listener
     */
    fun wrapActionListener() = object : ActionListener {
        override fun onAction(action: String, data: Any?) {
            actionListener?.onAction(action, data)
        }
    }
}