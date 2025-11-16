package com.sapienza.reverie.properties

import com.sapienza.reverie.R

class ApiProperties {
    companion object {
        // Use 'const' for compile-time constants for better performance
        const val API_BASE_PATH = "http://192.168.1.156:6001/reverie/api/"
        const val API_IMAGES_BASE_PATH = "http://192.168.1.156:6001/reverie/api/images?filename="
        var API_FOO_IMAGE_PATH = "drawable://${R.drawable.saint_rick}"
        const val GOOGLE_SIGN_IN_CLIENT_ID = "220913039576-jb8h800qkrp24tahrihfehan4s1h0jtf.apps.googleusercontent.com"
    }

}
