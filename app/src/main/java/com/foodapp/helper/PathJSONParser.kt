// References: https://javapapers.com/android/draw-path-on-google-maps-android-api/
package com.foodapp.helper

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import org.json.JSONException

class PathJSONParser {
    fun parse(jObject: JsonObject): Triple<List<List<LatLng>>, Int, Int> {
        val routes: MutableList<List<LatLng>> = ArrayList()
        var distance: Int = 0
        var duration: Int = 0
        try {
            val jRoutes = jObject["routes"].asJsonArray
            /** Traversing all routes  */
            for (route in jRoutes) {
                val jLegs = route.asJsonObject["legs"].asJsonArray
                val path: MutableList<LatLng> = ArrayList()
                /** Traversing all legs  */
                for (jLeg in jLegs) {
                    val jSteps = jLeg.asJsonObject["steps"].asJsonArray
                    /** Traversing all steps  */
                    for (step in jSteps) {
                        var polyline = ""
                        val step_obj = step.asJsonObject
                        distance += step_obj["distance"].asJsonObject["value"].asInt
                        duration += step_obj["duration"].asJsonObject["value"].asInt
                        polyline = step_obj["polyline"].asJsonObject["points"].asString
                        val list = decodePoly(polyline)
                        /** Traversing all points  */
                        for (l in list.indices) {
                            path.add(LatLng(list[l].latitude, list[l].longitude))
                        }
                    }
                    routes.add(path)
                }
            }
        } catch (e: JSONException) {
            Log.e("FOODAPP:PathJSONParser", e.stackTraceToString())
        } catch (e: Exception) {
            Log.e("FOODAPP:PathJSONParser", e.stackTraceToString())
        }
        return Triple(routes, distance, duration)
    }

    /**
     * Method Courtesy :
     * jeffreysambells.com/2010/05/27
     * /decoding-polylines-from-google-maps-direction-api-with-java
     */
    private fun decodePoly(encoded: String): List<LatLng> {
        val poly: MutableList<LatLng> = ArrayList()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }
}