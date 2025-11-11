package com.sapienza.reverie.properties

import com.sapienza.reverie.R

class ApiProperties{
    companion object {
        // Use 'const' for compile-time constants for better performance
        const val API_BASE_PATH = "http://10.0.2.2:6001/reverie/api"
        const val API_IMAGES_BASE_PATH = "http://10.0.2.2:6001/reverie/api/images"
        const val API_FOO_IMAGE_PATH = "drawable://${R.drawable.saint_rick}"
    }
        //const val API_REST_BASE_PATH = "http://10.0.2.2:6001/reverie/api/"


    }
}