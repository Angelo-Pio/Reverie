package com.sapienza.reverie.properties

import com.sapienza.reverie.R
import com.sapienza.reverie.BuildConfig


class ApiProperties {
    companion object {
        const val SERVER_IP = "172.29.86.149"

        val API_BASE_PATH = "http://${SERVER_IP}:6001/reverie/api/"
        val API_IMAGES_BASE_PATH = "http://${SERVER_IP}:6001/reverie/api/images?filename="
        val API_FOO_IMAGE_PATH = "drawable://${R.drawable.saint_rick}"

        val GOOGLE_SIGN_IN_CLIENT_ID = BuildConfig.GOOGLE_SIGN_IN_CLIENT_ID
        val CLIENT_ID = BuildConfig.CLIENT_ID
        val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        val GOOGLE_API_KEY = BuildConfig.GOOGLE_API_KEY
        val GOOGLE_CX = BuildConfig.GOOGLE_CX
    }
}
