package com.twoics.geo.api

import android.util.Log
import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.settings.Settings
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.LinkedBlockingQueue


private object ApiConstant {
    const val ROOT_URL = "https://api.opentripmap.com/0.1/"
    const val FOOD_PARAM_NAME = "foods"
    const val SPORT_PARAM_NAME = "sport"
    const val CULTURE_PARAM_NAME = "cultural"
    const val NATURE_PARAM_NAME = "natural"
}


class PlacesApi : IPlacesApi {
    private val client = OkHttpClient()

    private fun convertPlaceTypeToString(placeType: BookmarkType): String {
        when (placeType) {
            BookmarkType.FOOD -> {
                return ApiConstant.FOOD_PARAM_NAME
            }

            BookmarkType.SPORT -> {
                return ApiConstant.SPORT_PARAM_NAME
            }

            BookmarkType.CULTURE -> {
                return ApiConstant.CULTURE_PARAM_NAME
            }

            BookmarkType.NATURE -> {
                return ApiConstant.NATURE_PARAM_NAME
            }
        }
    }

    private fun convertPlacesToParams(places: ArrayList<BookmarkType>): String {
        val param = StringBuilder()
        places.forEach {
            param.append(convertPlaceTypeToString(it))
            param.append("%2C")
        }
        return param.toString()
    }

    private fun parsePlacesFromJSON(json: JSONArray): ArrayList<PlacesResponse> {
        fun getKind(kinds: String): BookmarkType {
            if (ApiConstant.FOOD_PARAM_NAME in kinds) {
                return BookmarkType.FOOD
            }
            if (ApiConstant.NATURE_PARAM_NAME in kinds) {
                return BookmarkType.NATURE
            }
            if (ApiConstant.CULTURE_PARAM_NAME in kinds) {
                return BookmarkType.CULTURE
            }
            return BookmarkType.SPORT
        }

        val places = ArrayList<PlacesResponse>()
        for (i in 0 until json.length()) {
            val place: JSONObject = json.getJSONObject(i)

            val prop = place.getJSONObject("properties")

            val xid = prop.getString("xid")
            val kind = getKind(prop.getString("kinds"))
            val geometry = place.getJSONObject("geometry")
            val coordinates = geometry.getJSONArray("coordinates")
            val lat = coordinates.getDouble(0)
            val long = coordinates.getDouble(1)

            places.add(
                PlacesResponse(
                    lat = lat,
                    long = long,
                    xid = xid,
                    type = kind
                )
            )
        }
        return places
    }

    override fun getPlaces(query: PlaceRequest): ArrayList<PlacesResponse> {
        val urlBuilder = StringBuilder()
        urlBuilder
            .append(ApiConstant.ROOT_URL)
            .append("en/")
            .append("places/radius?")

        val request = HttpUrl.parse(urlBuilder.toString())!!.newBuilder()
        request
            .addQueryParameter("radius", query.radius.toString())
            .addQueryParameter("lon", query.long.toString())
            .addQueryParameter("lat", query.lat.toString())
            .addEncodedQueryParameter("kinds", convertPlacesToParams(query.category))
            .addEncodedQueryParameter("apikey", Settings.WEATHER_API_KEY)

        val resultRequest = Request.Builder().url(request.build()).build()
        Log.d("REQUEST", resultRequest.toString())

        val queue = LinkedBlockingQueue<JSONArray>()
        Thread {
            client.newCall(resultRequest).execute().use { response ->
                val body = response.body()!!.string()
                val json = JSONObject(body)
                val jsonArray = json.getJSONArray("features")
                queue.add(jsonArray)
            }
        }.start()

        return parsePlacesFromJSON(queue.take())
    }
}