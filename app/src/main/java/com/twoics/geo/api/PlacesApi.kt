package com.twoics.geo.api

import android.util.Log
import com.twoics.geo.data.models.Bookmark
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
            val name = prop.getString("name")
            if (name.isBlank()) {
                continue
            }

            val xid = prop.getString("xid")
            val kind = getKind(prop.getString("kinds"))
            val geometry = place.getJSONObject("geometry")
            val coordinates = geometry.getJSONArray("coordinates")
            val long = coordinates.getDouble(0)
            val lat = coordinates.getDouble(1)

            places.add(
                PlacesResponse(
                    lat = lat, long = long, xid = xid, type = kind
                )
            )
        }
        return places
    }

    override fun getPlaces(query: PlaceRequest): ArrayList<PlacesResponse> {
        val urlBuilder = StringBuilder()
        urlBuilder.append(ApiConstant.ROOT_URL).append("en/") // TODO LANGUAGE
            .append("places/radius?")

        val request = HttpUrl.parse(urlBuilder.toString())!!.newBuilder()
        request.addQueryParameter("radius", query.radius.toString()).addQueryParameter("lon", query.long.toString())
            .addQueryParameter("lat", query.lat.toString())
            .addEncodedQueryParameter("kinds", convertPlacesToParams(query.category))
            .addEncodedQueryParameter("apikey", Settings.PLACES_API_KEY)

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

    private fun parseDetailToPlace(jsonObject: JSONObject, placeResponse: PlacesResponse): Bookmark {
        fun getValueOrDefault(jsonObject: JSONObject, field: String): String {
            return if (jsonObject.has(field)) jsonObject.getString(field) else "Undefined"
        }

        val address = jsonObject.getJSONObject("address")
        var description = "Empty description"
        if (jsonObject.has("info")) {
            description = jsonObject.getJSONObject("info").getString("descr")
        }

        return Bookmark(
            name = getValueOrDefault(jsonObject, "name"),
            country = getValueOrDefault(address, "country"),
            city = getValueOrDefault(address, "city"),
            street = getValueOrDefault(address, "road"),
            house = getValueOrDefault(address, "house_number"),
            description = description,
            type = placeResponse.type,
            long = placeResponse.long,
            lat = placeResponse.lat
        )
    }

    override fun getPlaceDetail(response: PlacesResponse): Bookmark {
        val urlBuilder = StringBuilder()
        urlBuilder.append(ApiConstant.ROOT_URL).append("en/").append("places/xid/").append(response.xid)
        val request =
            HttpUrl.parse(urlBuilder.toString())!!.newBuilder().addQueryParameter("apikey", Settings.PLACES_API_KEY)

        val resultRequest = Request.Builder().url(request.build()).build()
        val queue = LinkedBlockingQueue<JSONObject>()
        Thread {
            client.newCall(resultRequest).execute().use { response ->
                val body = response.body()!!.string()
                val json = JSONObject(body)
                queue.add(json)
            }
        }.start()

        return parseDetailToPlace(
            queue.take(), placeResponse = response
        )
    }
}