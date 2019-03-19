package com.projetdev.malo.musichall.api

import android.util.Log
import com.projetdev.malo.musichall.models.Artist
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.CountDownLatch
class ApiCall(private val url: String) {

    fun searchForArtists(value: String): ArrayList<Artist> {

        val artistList = ArrayList<Artist>()

        Log.e("MDR", this.url + "/search/artist/" + value)

        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/search/artist/" + value)
            .build()

        var countDownLatch = CountDownLatch(1)
        getClient()?.newCall(req)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERR", "Excuse me what the fuck")
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected response! Code $response")
                } else {
                    try {
                        val json = JSONArray(response.body()!!.string())
                        for (i in 0 until json.length()) {
                            val jsonObject = json.getJSONObject(i)
                            Log.i("LOL", jsonObject.getString("title"))
                            artistList.add(
                                Artist(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("title"),
                                    jsonObject.getString("uri")
                                )
                            )
                        }
                        countDownLatch.countDown()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            }
        })

        countDownLatch.await()
        return artistList
    }

    companion object {

        private var client: OkHttpClient? = null


        private fun getClient(): OkHttpClient? {
            if (client == null) {
                client = OkHttpClient()
            }
            return client
        }
    }
}

