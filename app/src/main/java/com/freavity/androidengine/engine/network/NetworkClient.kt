package com.freavity.androidengine.engine.network

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object NetworkClient {

    fun get(url: String, onResult: (String?) -> Unit) {
        thread(name = "NetworkClient-GET") {
            onResult(request(url, "GET", null))
        }
    }

    fun post(url: String, body: String, onResult: (String?) -> Unit) {
        thread(name = "NetworkClient-POST") {
            onResult(request(url, "POST", body))
        }
    }

    private fun request(url: String, method: String, body: String?): String? {
        var conn: HttpURLConnection? = null
        return try {
            conn = (URL(url).openConnection() as HttpURLConnection).apply {
                requestMethod = method
                connectTimeout = 10_000
                readTimeout = 10_000
                if (body != null) {
                    doOutput = true
                    setRequestProperty("Content-Type", "application/json")
                    outputStream.use { it.write(body.toByteArray(Charsets.UTF_8)) }
                }
            }
            val stream = if (conn.responseCode in 200..299) conn.inputStream else conn.errorStream
            stream?.let { BufferedReader(InputStreamReader(it)).use { r -> r.readText() } }
        } catch (_: Exception) {
            null
        } finally {
            conn?.disconnect()
        }
    }
}
