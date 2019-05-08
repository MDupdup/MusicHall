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

    fun getAlbum(id: Int): Album? {
        var album: Album? = null

        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/album/" + id)
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
                                    jsonObject.getString("url"),
                                    jsonObject.getInt("position"),
                                    timeToInt(jsonObject.getString("duration"))
                                )
                            )
                        }

                        // Get full track list
                        val artistList = ArrayList<Artist>();
                        val jsonArtistList = json.getJSONArray("artists")
                        for (i in 0 until jsonArtistList.length()) {
                            val jsonObject = jsonArtistList.getJSONObject(i)
                            artistList.add(
                                Artist(
                                    jsonObject.getString("name")
                                )
                            )
                        }

                        album = Album(
                            json.getString("Mbid"),
                            json.getString("Name"),
                            json.getString("Url"),
                            Artist(json.getString("Artist")),
                            images,
                            trackList,
                            tags,
                            json.getString("Summup"),
                            json.getString("Content")
                        )

                        countDownLatch.countDown()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            }
        })
        countDownLatch.await()
        return album
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

                    val discography = ArrayList<Album>()
                    val jsonArrayDiscs = json.getJSONArray("ReleasesMin")
                    for (i in 0 until jsonArrayDiscs.length()) {
                        val jsonDiscs = jsonArrayDiscs.getJSONObject(i)

                        discography.add(
                            Album(
                                jsonDiscs.getInt("ID"),
                                jsonDiscs.getString("Title"),
                                jsonDiscs.getInt("Year"),
                                jsonDiscs.getString("Thumb"),
                                arrayListOf(Artist(json.getString("name"))),
                                null
                            )
                        )
                    }

                    val members = ArrayList<Member>()
                    val jsonArrayMembers = json.getJSONArray("Members")
                    for (i in 0 until jsonArrayMembers.length()) {
                        val jsonMembers = jsonArrayMembers.getJSONObject(i)

                        members.add(
                            Member(
                                jsonMembers.getInt("id"),
                                jsonMembers.getString("name"),
                                jsonMembers.getString("resource_url"),
                                jsonMembers.getBoolean("active")
                            )
                        )
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

    private fun searchForReleases(value: String?): ArrayList<Result> {

        val discList = ArrayList<Result>()


        if (value == "" || value == null) return discList


        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/search/release/" + value)
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

                            val titlePair = parseReleaseTitle(jsonObject.getString("title"))

                            // Get full styles list
                            val jsonStyles = jsonObject.getJSONArray("Style")
                            val stylesList = mutableListOf<String>();
                            for (i in 0 until jsonStyles.length()) {
                                stylesList += jsonStyles.getString(i)
                            }

                            discList.add(
                                Album(
                                    jsonObject.getInt("id"),
                                    titlePair.second,
                                    parseYear(jsonObject.getString("Year")),
                                    jsonObject.getString("cover_image"),
                                    arrayListOf(Artist(titlePair.first)),
                                    stylesList
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
        if (mode == 0) {
            return searchForArtists(value)
        } else if (mode == 1) {
            return searchForReleases(value)
        }

        return ArrayList<Result>()
    }


    private fun searchForArtists(value: String? = ""): ArrayList<Result> {

        val artistList = ArrayList<Result>()

        Log.e("MDR", this.url + "/search/artist/" + value)

        if (value == "" || value == null) return artistList

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


    private fun parseYear(year: String): Int {
        return if (year == "") 0 else parseInt(year)
    }

    private fun parseReleaseTitle(title: String): Pair<String, String> {
        val parts: List<String> = title.split("-")

        val pair = Pair(parts[0], parts[1])

        return pair
    }

    private fun timeToInt(time: String): Int {
        val splitTime = time.split(":")
        val finalTime = parseInt(splitTime[0]) * 60 + parseInt(splitTime[1])

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