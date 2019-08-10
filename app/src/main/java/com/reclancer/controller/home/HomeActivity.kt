package com.reclancer.controller.home

import android.os.Bundle
import android.view.MenuItem
import com.reclancer.R
import com.reclancer.controller.items.ItemListFragment
import com.reclancer.controller.base.BaseActivity

class HomeActivity : BaseActivity() {

    private val homeFragment = HomeFragment(this)
    private val productListFragment = ItemListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHomeFragment()
    }

    private fun showHomeFragment() {
        title = getString(R.string.str_home)
        setHomeIcon(R.drawable.ic_close_white_24dp)
        showFragment(homeFragment)
    }

    private fun showPLPFragment() {
        title = getString(R.string.str_product_list)
        setHomeIcon(R.drawable.ic_arrow_back_white_24dp)
        showFragment(productListFragment)
    }

    override fun handleBackPressed() {
        when {
            productListFragment.isVisible -> showHomeFragment()
            homeFragment.isVisible -> superOnBackPressed()
        }
    }

    override fun onPerformAction(action: String, data: Any?): Boolean {
        when(action) {
            SHOW_HOME_FRAGMENT -> showHomeFragment()
            SHOW_PLP_FRAGMENT -> showPLPFragment()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when {
        item.itemId == android.R.id.home && homeFragment.isVisible -> {
            finish()
            true
        }
        productListFragment.isVisible -> {
            showHomeFragment()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = HomeActivity::getLocalClassName

        val SHOW_PLP_FRAGMENT = "$TAG.show_plp_fragment"
        val SHOW_HOME_FRAGMENT = "$TAG.show_home_fragment"
    }
}
