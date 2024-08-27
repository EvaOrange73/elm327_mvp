package com.example.elm327.util

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class RawResourcesReader {
    companion object {
        @Throws(IOException::class)
        private fun convertStreamToString(`is`: InputStream): String {
            val baos = ByteArrayOutputStream()
            var i = `is`.read()
            while (i != -1) {
                baos.write(i)
                i = `is`.read()
            }
            return baos.toString()
        }

        fun readLines(context: Context, rawResource: Int): List<String> {
            return convertStreamToString(context.resources.openRawResource(rawResource)).split("\n")
        }
    }
}