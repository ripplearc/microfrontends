package com.ripplearc.heavy.toolbelt.location

import com.github.davidmoten.geo.Coverage
import com.github.davidmoten.geo.GeoHash
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import java.lang.Math.max
import java.lang.Math.min

data class BoundingBox(val topLeft: LatLng, val bottomRight: LatLng) {
    companion object
}

/**
 * BoundingBox that encapsulates the United States.
 */
val BoundingBox.Companion.us
    get() = BoundingBox(
        topLeft = LatLng(-171.71111, 18.91619),
        bottomRight = LatLng(-66.96466, 71.35776)
    )

/**
 * Convert BoundingBox to Google Map PolygonOptions
 */
val BoundingBox.polygon: PolygonOptions
    get() = PolygonOptions()
        .add(bottomRight)
        .add(LatLng(topLeft.latitude, bottomRight.longitude))
        .add(topLeft)
        .add(LatLng(bottomRight.latitude, topLeft.longitude))

/**
 * Compute the GeoHash Coverage of the BoundingBox
 */
val BoundingBox.coverage: Coverage
    get() = GeoHash.coverBoundingBox(
        topLeft.latitude,
        topLeft.longitude,
        bottomRight.latitude,
        bottomRight.longitude
    )

/**
 * Center loation of the BoundingBox
 */
val BoundingBox.center: LatLng
    get() = LatLng(
        doubleArrayOf(topLeft.latitude, bottomRight.latitude).average(),
        doubleArrayOf(topLeft.longitude, bottomRight.longitude).average()
    )

/**
 * Get bouding rectangle using Drupal Earth Algorithm
 * @see https://www.rit.edu/drupal/api/drupal/sites%21all%21modules%21location%21earth.inc/7.54
 *
 * @param lat
 * @param lng
 * @param radius
 * @return
 */
fun LatLng.boundingBox(distanceMeter: Double): BoundingBox {
    val lng = Math.toRadians(longitude)
    val lat = Math.toRadians(latitude)
    val radius = earthRadius(lat) ?: return BoundingBox(this, this)
    val distanceKm = distanceMeter / 1000

    val retLats = earthLatitudeRange(lat, radius, distanceKm)
    val retLngs = earthLongitudeRange(lat, lng, radius, distanceKm)
    val topLeft = LatLng(Math.toDegrees(retLats[0]), Math.toDegrees(retLngs[0]))
    val bottomRight = LatLng(Math.toDegrees(retLats[1]), Math.toDegrees(retLngs[1]))
    val topLeftLatitude = max(topLeft.latitude, bottomRight.latitude)
    val bottomRightLatitude = min(topLeft.latitude, bottomRight.latitude)
    val topLeftLongitude = min(topLeft.longitude, bottomRight.longitude)
    val bottomRightLongitude = max(topLeft.longitude, bottomRight.longitude)
    return BoundingBox(
        LatLng(topLeftLatitude, topLeftLongitude),
        LatLng(bottomRightLatitude, bottomRightLongitude)
    )
}


/**
 * Calculate latitude range based on earths radius at a given point
 * @param latitude
 * @param longitude
 * @param distance
 * @return
 */
fun earthLatitudeRange(lat: Double, radius: Double, distance: Double): DoubleArray {
    // Estimate the min and max latitudes within distance of a given location.

    val angle = distance / radius
    var minlat = lat - angle
    var maxlat = lat + angle
    val rightAngle = Math.PI / 2
    // Wrapped around the south pole.
    if (minlat < -rightAngle) {
        val overshoot = -minlat - rightAngle
        minlat = -rightAngle + overshoot
        if (minlat > maxlat) {
            maxlat = minlat
        }
        minlat = -rightAngle
    }
    // Wrapped around the north pole.
    if (maxlat > rightAngle) {
        val overshoot = maxlat - rightAngle
        maxlat = rightAngle - overshoot
        if (maxlat < minlat) {
            minlat = maxlat
        }
        maxlat = rightAngle
    }
    return doubleArrayOf(minlat, maxlat)
}

/**
 * Calculate longitude range based on earths radius at a given point
 * @param lat
 * @param lng
 * @param earth_radius
 * @param distance
 * @return
 */
fun earthLongitudeRange(
    lat: Double,
    lng: Double,
    earth_radius: Double,
    distance: Double
): DoubleArray {
    // Estimate the min and max longitudes within distance of a given location.
    val radius = earth_radius * Math.cos(lat)

    var angle: Double
    if (radius > 0) {
        angle = Math.abs(distance / radius)
        angle = Math.min(angle, Math.PI)
    } else {
        angle = Math.PI
    }
    var minlong = lng - angle
    var maxlong = lng + angle
    if (minlong < -Math.PI) {
        minlong += Math.PI * 2
    }
    if (maxlong > Math.PI) {
        maxlong -= Math.PI * 2
    }
    return doubleArrayOf(minlong, maxlong)
}

/**
 * Calculate earth radius at given latitude
 * @param latitude
 * @return
 */
fun earthRadius(latitude: Double): Double? {
    // Estimate the Earth's radius at a given latitude.
    // Default to an approximate average radius for the United States.
    val lat = Math.toRadians(latitude)

    val x = Math.cos(lat) / 6378137.0
    val y = Math.sin(lat) / (6378137.0 * (1 - 1 / 298.257223563))

    //Make sure earth's radius is in km , not meters
    return 1 / Math.sqrt(x * x + y * y) / 1000
}
