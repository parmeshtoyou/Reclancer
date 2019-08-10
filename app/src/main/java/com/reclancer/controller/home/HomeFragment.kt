package com.reclancer.controller.home

import android.os.Bundle
import android.view.View
import com.reclancer.R
import com.reclancer.common.ActionListener
import com.reclancer.common.EMPTY_STRING
import com.reclancer.controller.base.BaseFragment
import kotlinx.android.synthetic.main.home_fragment_layout.*

class HomeFragment(actionListener : ActionListener) : BaseFragment() {
    override fun layoutResource() = R.layout.home_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gotoListFragment.setOnClickListener {
            actionListener?.onAction(HomeActivity.SHOW_PLP_FRAGMENT, EMPTY_STRING)
        }
    }
}