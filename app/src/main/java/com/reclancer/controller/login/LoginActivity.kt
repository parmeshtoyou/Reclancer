package com.reclancer.controller.login

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.reclancer.R
import com.reclancer.common.startActivityForResult
import com.reclancer.controller.base.BaseActivity
import com.reclancer.utils.isUserLoggedIn
import com.reclancer.utils.userEmailId
import com.reclancer.utils.userPwd
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener {
            userEmailId = userEmail.text.toString()
            userPwd = password.text.toString()

            if (isUserLoggedIn()) {
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Please Check EmailId/Password.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        const val LOGIN_REQUEST_CODE = 10001

        fun startLoginActivity(callingActivity: Activity) {
            callingActivity.startActivityForResult(LoginActivity::class.java, LOGIN_REQUEST_CODE)
        }
    }

    override fun handleBackPressed() {
        superOnBackPressed()
    }
}
