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
import kotlin.random.Random

class ApiCall(private val url: String) {

    fun getAlbum(id: String): Album? {
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
                        val jsonTrackList = json.getJSONArray("Tracks")
                        for (j in 0 until jsonTrackList.length()) {
                            val jsonObject = jsonTrackList.getJSONObject(j)

                            val position =
                                if (jsonObject.getString("Position") == "") (j + 1).toString() else jsonObject.getString(
                                    "Position"
                                )
                            trackList.add(
                                Track(
                                    jsonObject.getString("Name"),
                                    jsonObject.getString("Url"),
                                    position,
                                    parseInt(jsonObject.getString("Duration"))
                                )
                            )
                        }

                        val tags = ArrayList<String>()
                        val jsonTags = json.getJSONArray("Tags")
                        for (i in 0 until jsonTags.length()) {
                            if (jsonTags.getString(i) in unwantedTags) continue
                            tags.add(jsonTags.getString(i))
                        }

                        album = Album(
                            json.getString("Mbid"),
                            json.getString("Name"),
                            json.getString("Url"),
                            getYear(),
                            Artist(json.getString("Artist")),
                            getImages(json.getJSONArray("Images")),
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

    fun getArtist(id: String): Artist? {
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

                    // Get similar artists
                    val similarArtists = ArrayList<Artist>()
                    val jsonSimilarArtists = json.getJSONArray("Similar")
                    for (i in 0 until jsonSimilarArtists.length()) {
                        val similarArtist = jsonSimilarArtists.getJSONObject(i)

                        similarArtists.add(
                            Artist(
                                similarArtist.getString("Name"),
                                getImages(similarArtist.getJSONArray("Images"))
                            )
                        )
                    }

                    // Get albums
                    val albums = ArrayList<Album>()
                    val jsonAlbums = json.getJSONArray("Albums")
                    for (i in 0 until jsonSimilarArtists.length()) {
                        val album = jsonAlbums.getJSONObject(i)

                        albums.add(
                            Album(
                                album.getString("Name"),
                                album.getString("Mbid"),
                                album.getString("Url"),
                                getImages(album.getJSONArray("Images"))
                            )
                        )
                    }

                    for (i in 0 until jsonSimilarArtists.length()) {
                        val album = jsonAlbums.getJSONObject(i)

                        albums.add(
                            Album(
                                album.getString("Name"),
                                album.getString("Mbid"),
                                album.getString("Url"),
                                getImages(album.getJSONArray("Images"))
                            )
                        )
                    }

                    artist = Artist(
                        json.getString("Mbid"),
                        json.getString("Name"),
                        json.getString("Url"),
                        getImages(json.getJSONArray("Images")),
                        json.getString("PlayCount"),
                        json.getBoolean("IsOnTour"),
                        similarArtists,
                        json.getString("Summup"),
                        json.getString("Content"),
                        albums,
                        null//getTags(json.getJSONArray("Tags"))
                    )

                    countDownLatch.countDown()
                }
            }
        })
        countDownLatch.await()
        return artist
    }

    fun search(mode: Int, value: String? = ""): ArrayList<Item> {
        return when (mode) {
            0 -> searchForArtists(value)
            1 -> searchForAlbums(value)
            2 -> justSearchFfs(value)
            else -> ArrayList()
        }
    }

    private fun justSearchFfs(value: String? = ""): ArrayList<Item> {
        val wholeSearchList = ArrayList<Item>()

        //wholeSearchList.addAll(searchForArtists(value))
        wholeSearchList.addAll(searchForAlbums(value))

        //wholeSearchList.shuffle()

        return wholeSearchList
    }

    private fun searchForArtists(value: String? = ""): ArrayList<Item> {

        val artistList = ArrayList<Item>()

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
                                    jsonObject.getString("Mbid"),
                                    jsonObject.getString("Name"),
                                    jsonObject.getString("Url"),
                                    jsonObject.getString("PlayCount"),
                                    getImages(jsonObject.getJSONArray("Images"))
                                )
                            )
                        }
                        countDownLatch.countDown()
                    } catch (e: JSONException) {
                        Log.e("PTN", e.message, e)
                    }
                }
            }
        })

        countDownLatch.await()
        return artistList
    }

    private fun searchForAlbums(value: String?): ArrayList<Item> {

        val discList = ArrayList<Item>()


        if (value == "" || value == null) return discList


        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/search/album/" + value)
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

                            discList.add(
                                Album(
                                    jsonObject.getString("Mbid"),
                                    jsonObject.getString("Name"),
                                    jsonObject.getString("Url"),
                                    getYear(),
                                    Artist(jsonObject.getString("Artist")),
                                    getImages(jsonObject.getJSONArray("Images"))
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

    private fun getImages(jsonImages: JSONArray): Map<String, String> {
        val imagesMap = mutableMapOf<String, String>()
        for (i in 0 until jsonImages.length()) {
            val image = jsonImages.getJSONObject(i)
            imagesMap.put(image.getString("Size"), image.getString("Url"))
        }

        return imagesMap
    }

    private fun getTags(jsonTags: JSONArray): ArrayList<String> {

        val tags = arrayListOf<String>()
        for (i in 0 until jsonTags.length()) {
            val tag = jsonTags.getJSONObject(i)
            if (tag.getString("Name") !in unwantedTags)
                tags[i] = tag.getString("Name")
        }

        return tags
    }

    private fun getYear(): Int {
        return Random.nextInt(1970, 2020)
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

        private val unwantedTags = arrayListOf("albums I own", "favourite albums", "favorite albums")

        private var client: OkHttpClient? = OkHttpClient()

        private fun getClient(): OkHttpClient? {
            return client
        }
    }
}
