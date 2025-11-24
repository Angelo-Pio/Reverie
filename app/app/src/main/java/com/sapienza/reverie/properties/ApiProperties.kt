package com.sapienza.reverie.properties

import com.sapienza.reverie.R

class ApiProperties {
    companion object {
        // Use 'const' for compile-time constants for better performance

        const val SERVER_IP = "192.168.1.37"

        var API_BASE_PATH = "http://${SERVER_IP}:6001/reverie/api/"
        var API_IMAGES_BASE_PATH = "http://${SERVER_IP}:6001/reverie/api/images?filename="
        var API_FOO_IMAGE_PATH = "drawable://${R.drawable.saint_rick}"
        const val GOOGLE_SIGN_IN_CLIENT_ID =
            "220913039576-jb8h800qkrp24tahrihfehan4s1h0jtf.apps.googleusercontent.com"

        const val CLIENT_ID = "220913039576-2ue0inhmdus26r3rd0jsb0mn3a56cqhc.apps.googleusercontent.com"
        const val CLIENT_SECRET = "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"

        // Search Engine API
        const val GOOGLE_API_KEY = "AIzaSyABN5LQXUjAfN301yX9MmPi7b1BsJEP32A"
        const val GOOGLE_CX = "97904ceb3c1ee4d78"
    }

}
