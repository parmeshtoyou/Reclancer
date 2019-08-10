package com.reclancer.utils

import com.reclancer.common.EMPTY_STRING

var userPwd: String = EMPTY_STRING
var userEmailId : String = EMPTY_STRING

fun isUserLoggedIn() = userPwd.isNotBlank() && userEmailId.isNotEmpty()