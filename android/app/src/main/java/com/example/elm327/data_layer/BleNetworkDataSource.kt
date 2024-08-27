package com.example.elm327.data_layer

import android.location.Location
import android.util.Log
import com.example.elm327.util.elm.ObdPids
import com.example.elm327.util.value.Value
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Call
import okhttp3.Response
import java.io.IOException


class BleNetworkDataSource {
    companion object {
        private val LOG_TAG = "ble network data source"
        private val client = OkHttpClient()

        fun updatePid(
            id: String,
            location: Location?,
            timestamp: Long,
            pid: ObdPids,
            values: List<Value>
        ) {
            val httpUrl: HttpUrl = HttpUrl.Builder()
                .scheme("https")
                .host("mtrack-test.oqode.ru")
                .port(5055)
                .addQueryParameter("id", id)
//                .addQueryParameter("lat", location.latitude.toString())
//                .addQueryParameter("lon", location.longitude.toString())
                .addQueryParameter("lat", "48.8566")
                .addQueryParameter("lon", "2.3522")
                .addQueryParameter("timestamp", timestamp.toString())
                .addQueryParameter("pid", pid.pid)
                .addQueryParameter("values", values.joinToString(" ") { it.toString() })
                .build()

//            Log.i(LOG_TAG, httpUrl.toString())

            val request: Request = Request.Builder().url(httpUrl).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(LOG_TAG, e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) {
                            Log.e(LOG_TAG, "request failed")
                        }
                    }
                }
            })

        }
    }

}