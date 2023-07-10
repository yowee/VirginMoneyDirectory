package com.virginmoney.virginmoneydirectory.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val email :String,
    val userid: String
    //... other data fields that may be accessible to the UI
)