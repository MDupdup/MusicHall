package com.projetdev.malo.musichall.api

import android.util.Log
import com.google.gson.*
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
import okhttp3.RequestBody

import com.google.gson.reflect.TypeToken


class ApiCall(private val url: String) {

    @Throws(IOException::class)
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

    @Throws(IOException::class)
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

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected response! Code $response")
                } else {
                    val json = JSONObject(response.body()!!.string())

                    // Get similar artists
                    val similarArtists = ArrayList<Artist>()
                    /*val jsonSimilarArtists = json.getJSONArray("Similar")
                    for (i in 0 until jsonSimilarArtists.length()) {
                        val similarArtist = jsonSimilarArtists.getJSONObject(i)

                        similarArtists.add(
                            Artist(
                                similarArtist.getString("Name"),
                                getImages(similarArtist.getJSONArray("Images"))
                            )
                        )
                    }*/

                    // Get albums
                    val albums = ArrayList<Album>()
                    val jsonAlbums = json.getJSONArray("Albums")
                    for (i in 0 until jsonAlbums.length()) {
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


    fun insertArtist(artist: Artist) {
        val gson = GsonBuilder().setPrettyPrinting().create()

        val obj: JsonObject = Gson().fromJson(gson.toJson(artist), JsonElement::class.java).asJsonObject

        val imagesArray = arrayListOf<String>("small", "medium", "large", "extralarge", "mega", "")
        val newArray = JsonArray()
        for (i in 0 until artist.images.size) {
            val newImage = JsonObject()
            newImage.addProperty("Url", artist.images[imagesArray[i]])
            newImage.addProperty("Size", imagesArray[i])
            newArray.add(newImage)
        }
        obj.add("images", newArray)

        val albumImagesArray = arrayListOf<String>("small", "medium", "large", "extralarge", "mega", "")
        val newAlbumArray = JsonArray()
        for (i in 0 until artist.albums.size) {
            val newImageArray = JsonArray()
            for (j in 0 until artist.albums[i].images.size) {
                val newImage = JsonObject()
                newImage.addProperty("Url", artist.albums[i].images[albumImagesArray[j]])
                newImage.addProperty("Size", albumImagesArray[j])
                newImageArray.add(newImage)
            }
            val album = Gson().fromJson(gson.toJson(artist.albums[i]), JsonElement::class.java).asJsonObject
            album.add("images", newImageArray)

            newAlbumArray.add(album)
        }
        obj.add("albums", newAlbumArray)

        System.out.println("json " + gson.toJson(artist))


        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString())
        val request = Request.Builder()
            .url(this.url + "/favorites/add/artist")
            .post(body)
            .build()
        getClient()?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", "Excuse me what the fuck", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("COOL", "It's Gone!")
            }
        })
    }

    fun insertAlbum(album: Album) {
        val gson = GsonBuilder().setPrettyPrinting().create()

        val obj: JsonObject = Gson().fromJson(gson.toJson(album), JsonElement::class.java).asJsonObject

        val imagesArray = arrayListOf<String>("small", "medium", "large", "extralarge", "mega", "")
        val newArray: JsonArray? = JsonArray()
        for (i in 0 until album.images.size) {
            val newImage = JsonObject()
            newImage.addProperty("Url", album.images[imagesArray[i]])
            newImage.addProperty("Size", imagesArray[i])
            newArray?.add(newImage)
        }
        obj.add("images", newArray)
        obj.addProperty("artist", album.artist.name)

        System.out.println("json " + gson.toJson(album))

        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString())
        val request = Request.Builder()
            .url(this.url + "/favorites/add/album")
            .post(body)
            .build()
        getClient()?.newCall(request)?.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", "Excuse me what the fuck", e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("COOL", "It's Gone!")
            }
        })
    }


    fun getArtistCollection(): ArrayList<Artist> {
        val artistCollection = ArrayList<Artist>()


        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/favorites/get/artist")
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

                            val obj = json.getJSONObject(i)

                            // Get similar artists
                            val similarArtists = ArrayList<Artist>()
                            val jsonSimilarArtists = obj.getJSONArray("Similar")
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
                            val jsonAlbums = obj.getJSONArray("Albums")
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

                            artistCollection.add(
                                Artist(
                                    obj.getString("Mbid"),
                                    obj.getString("Name"),
                                    obj.getString("Url"),
                                    getImages(obj.getJSONArray("Images")),
                                    obj.getString("PlayCount"),
                                    obj.getBoolean("IsOnTour"),
                                    similarArtists,
                                    obj.getString("Summup"),
                                    obj.getString("Content"),
                                    albums,
                                    null//getTags(json.getJSONArray("Tags"))
                                )
                            )

                            countDownLatch.countDown()
                        }

                        countDownLatch.countDown()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })

        countDownLatch.await()
        return artistCollection

    }

    fun getAlbumCollection(): ArrayList<Album> {

        val albumCollection = ArrayList<Album>()


        val req = Request.Builder()
            .header("Content-Type", "application/json")
            .url(this.url + "/favorites/get/album")
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

                            val obj = json.getJSONObject(i)

                            // Get full track list
                            val trackList = ArrayList<Track>();
                            val jsonTrackList = obj.getJSONArray("Tracks")
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
                            val jsonTags = obj.getJSONArray("Tags")
                            for (i in 0 until jsonTags.length()) {
                                if (jsonTags.getString(i) in unwantedTags) continue
                                tags.add(jsonTags.getString(i))
                            }

                            albumCollection.add(
                                Album(
                                    obj.getString("Mbid"),
                                    obj.getString("Name"),
                                    obj.getString("Url"),
                                    getYear(),
                                    Artist(obj.getString("Artist")),
                                    getImages(obj.getJSONArray("Images")),
                                    trackList,
                                    tags,
                                    obj.getString("Summup"),
                                    obj.getString("Content")
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
        return albumCollection
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


