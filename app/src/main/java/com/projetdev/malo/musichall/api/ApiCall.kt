package com.projetdev.malo.musichall.api

import android.util.Log
import com.projetdev.malo.musichall.models.Artist
import com.projetdev.malo.musichall.models.Disc
import com.projetdev.malo.musichall.models.Track
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.lang.Integer.parseInt
import java.util.ArrayList
import java.util.concurrent.CountDownLatch

class ApiCall(private val url: String) {

    fun getRelease(id: Int): Disc? {
        var disc: Disc? = null

        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/release/" + id)
            .build()

        val countDownLatch = CountDownLatch(1)
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
                        val json = JSONObject(response.body()!!.string())

                        // Get full track list
                        val trackList = ArrayList<Track>();
                        val jsonTrackList = json.getJSONArray("tracklist")
                        for (j in 0 until jsonTrackList.length()) {
                            val jsonObject = jsonTrackList.getJSONObject(j)
                            trackList.add(
                                Track(
                                    jsonObject.getString("title"),
                                    jsonObject.getInt("position"),
                                    timeToInt(jsonObject.getString("duration"))
                                )
                            )
                        }
                        // Get full label list
                        val jsonLabelList = json.getJSONArray("labels")
                        val labelList = mutableListOf<String>();
                        System.err.println(jsonLabelList.length())
                        for (i in 0 until jsonLabelList.length()) {
                            System.err.println(jsonLabelList.getJSONObject(i).getString("name"))
                            labelList += jsonLabelList.getJSONObject(i).getString("name")
                        }

                        // Get full track list
                        val artistList = ArrayList<Artist>();
                        val jsonArtistList = json.getJSONArray("artists")
                        for (i in 0 until jsonArtistList.length()) {
                            val jsonObject = jsonArtistList.getJSONObject(i)
                            artistList.add(
                                Artist(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("resource_url")
                                )
                            )
                        }

                        disc = Disc(
                            id,
                            json.getString("title"),
                            json.getInt("year"),
                            json.getString("thumb"),
                            artistList,
                            getArrayFromJson(json.getJSONArray("styles")),
                            getArrayFromJson(json.getJSONArray("formats").getJSONObject(0).getJSONArray("descriptions")),
                            json.getString("country"),
                            json.getString("uri"),
                            labelList,
                            trackList
                        )

                        countDownLatch.countDown()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            }
        })
        countDownLatch.await()
        return disc
    }

/*    fun searchForReleases(value: String): ArrayList<Disc> {

        val discList = ArrayList<Disc>()

        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/search/release/" + value)
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
                            discList.add(
                                Disc(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("title"),
                                    parseInt(jsonObject.getString("year")),
                                    jsonObject.getString("thumb"),
                                    jsonObject.getString("thumb"),
                                    jsonObject.getString("thumb"),
                                    jsonObject.getString("thumb"),
                                    jsonObject.getString("thumb"),
                                    jsonObject.getString("thumb"),
                                    jsonObject.getString("thumb"),
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
        return discList
    }*/

    fun searchForArtists(value: String?): ArrayList<Artist> {

        val artistList = ArrayList<Artist>()

        Log.e("MDR", this.url + "/search/artist/" + value)

        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/search/artist/" + value)
            .build()

        val countDownLatch = CountDownLatch(1)
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
                                    jsonObject.getString("uri"),
                                    jsonObject.getString("cover_image")
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

    private fun timeToInt(time: String): Int {
        val splittedTime = time.split(":")
        val finalTime = parseInt(splittedTime[0])*60 + parseInt(splittedTime[1])

        return finalTime
    }

    private fun getArrayFromJson(jsonArray: JSONArray): List<String>? {
        val stringList = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) stringList += jsonArray.getString(i)

        return stringList
    }

    companion object {

        private var client: OkHttpClient? = OkHttpClient()

        private fun getClient(): OkHttpClient? {
            return client
        }
    }
}