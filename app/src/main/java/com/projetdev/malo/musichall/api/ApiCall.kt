package com.projetdev.malo.musichall.api

import android.util.Log
import com.projetdev.malo.musichall.models.*
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

    fun getArtist(id: Int): Artist? {
        var artist: Artist? = null

        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/artist/" + id)
            .build()

        val countDownLatch = CountDownLatch(1)
        getClient()?.newCall(req)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERR", "Excuse me what the fuck")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected response! Code $response")
                } else {
                    val json = JSONObject(response.body()!!.string())

                    val discography = ArrayList<Disc>()
                    val jsonArrayDiscs = json.getJSONArray("ReleasesMin")
                    for (i in 0 until jsonArrayDiscs.length()) {
                        val jsonDiscs = jsonArrayDiscs.getJSONObject(i)

                        discography.add(Disc(
                            jsonDiscs.getInt("ID"),
                            jsonDiscs.getString("Title"),
                            jsonDiscs.getInt("Year"),
                            jsonDiscs.getString("Thumb")
                        ))
                    }

                    val members = ArrayList<Member>()
                    val jsonArrayMembers = json.getJSONArray("Members")
                    for (i in 0 until jsonArrayMembers.length()) {
                        val jsonMembers = jsonArrayMembers.getJSONObject(i)

                        members.add(Member(
                            jsonMembers.getInt("id"),
                            jsonMembers.getString("name"),
                            jsonMembers.getString("resource_url"),
                            jsonMembers.getBoolean("active")
                        ))
                    }

                    artist = Artist(
                        json.getInt("id"),
                        json.getString("name"),
                        "",
                        "",
                        json.getString("profile"),
                        discography,
                        members
                    )

                    countDownLatch.countDown()
                }
            }
        })
        countDownLatch.await()
        return artist
    }

   fun searchForReleases(value: String?): ArrayList<Result> {

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
    }

    fun search(mode: Int, value: String? = ""): ArrayList<Result> {
        if(mode == 0) {
            return searchForArtists(value)
        } else if(mode == 1) {
            return searchForReleases(value)
        }

        return ArrayList<Result>()
    }


    private fun searchForArtists(value: String? = ""): ArrayList<Result> {

        val artistList = ArrayList<Result>()

        Log.e("MDR", this.url + "/search/artist/" + value)

        if(value == "" || value == null) return artistList

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
                            artistList.add(
                                Artist(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("title"),
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