package com.example.elm327.data_layer

import android.location.Location
import android.util.Log
import com.example.elm327.util.DecodedPidValue
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
            location: Location,
            decodedPidValue: DecodedPidValue
        ) {
            val httpUrl: HttpUrl = HttpUrl.Builder()
                .scheme("https")
                .host("mtrack-test.oqode.ru")
                .port(5055)
                .addQueryParameter("id", id)
                .addQueryParameter("lat", location.latitude.toString())
                .addQueryParameter("lon", location.longitude.toString())
                .addQueryParameter("timestamp", decodedPidValue.timestamp.toString())
                .addQueryParameter("pid", decodedPidValue.pid.pid)
                .addQueryParameter("values", decodedPidValue.valuesAsString(UnitOfMeasurement.SI))
                .addQueryParameter("rawData", decodedPidValue.rawData)
                .build()

            request(httpUrl)

        }

        fun updateLocation(
            id: String,
            location: Location,
        ) {
            val httpUrl: HttpUrl = HttpUrl.Builder()
                .scheme("https")
                .host("mtrack-test.oqode.ru")
                .port(5055)
                .addQueryParameter("id", id)
                .addQueryParameter("lat", location.latitude.toString())
                .addQueryParameter("lon", location.longitude.toString())
                .addQueryParameter("timestamp", location.time.toString())
                .build()

            request(httpUrl)
        }


        private fun request(httpUrl: HttpUrl){
            Log.i(LOG_TAG, httpUrl.toString())

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