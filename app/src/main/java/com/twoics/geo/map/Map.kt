package com.twoics.geo.map

import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.twoics.geo.R
import com.twoics.geo.data.models.Bookmark
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.DelayedMapListener
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon


private object MapConstants {
    const val WEST_BORDER = -180.0
    const val SOUTH_BORDER = -85.0
    const val NORTH_BORDER = 85.0
    const val EAST_BORDER = 180.0
    const val MAX_ZOOM_LEVEL = 20.0
    const val MIN_ZOOM_LEVEL = 4.0
    const val START_ZOOM = 12.0
    const val SEARCH_AREA_ID = "map"

    val SEARCH_AREA_COLOR = Color.argb(100, 158, 173, 200)
    val SEARCH_AREA_BORDER_COLOR = Color.argb(180, 158, 173, 200)
}


class Map(
    defaultAreaRadius: Double,
    defaultMapLocation: GeoPoint
) : IMap {
    private lateinit var map: MapView
    private lateinit var searchAreaPolygon: Polygon

    private var foundedPlaces = ArrayList<Bookmark>()
    private var zoom: Double = MapConstants.START_ZOOM

    override var areaRadius: Double = defaultAreaRadius
    override var centerMapLocation: GeoPoint = defaultMapLocation
        private set

    private fun drawCurrentPlaces() {
        fun drawPlace(bookmark: Bookmark) {
            val marker = Marker(map)
            marker.position = GeoPoint(bookmark.lat, bookmark.long)
            map.overlays.add(marker);
        }

        this.foundedPlaces.forEach {
            drawPlace(bookmark = it)
        }
        map.invalidate()
    }

    override fun drawFoundedPlaces(places: ArrayList<Bookmark>) {
        foundedPlaces = places
        drawCurrentPlaces()
    }

    override fun clearPlaces() {
        this.foundedPlaces.clear()

        TODO("Not implemented")
    }

    override fun focusOnPlace(place: Bookmark) {
        TODO("Not yet implemented")
    }

    private fun drawCircleByRadius() {
        this.searchAreaPolygon.points = Polygon.pointsAsCircle(
            GeoPoint(
                centerMapLocation.latitude,
                centerMapLocation.longitude
            ),
            areaRadius
        )
        searchAreaPolygon.fillColor = MapConstants.SEARCH_AREA_COLOR
        searchAreaPolygon.strokeColor = MapConstants.SEARCH_AREA_BORDER_COLOR
        map.invalidate()
    }

    override fun drawSearchCircle(radius: Double) {
        areaRadius = radius
        drawCircleByRadius()
    }

    @Composable
    override fun redrawMap(): MapView {
        this.map = generateMap()
        configureMap()
        return this.map
    }

    private fun configureMap() {
        fun setScrollBorders() {
            map.setScrollableAreaLimitDouble(
                BoundingBox(
                    MapConstants.NORTH_BORDER,
                    MapConstants.EAST_BORDER,
                    MapConstants.SOUTH_BORDER,
                    MapConstants.WEST_BORDER
                )
            )
        }

        fun setScaleBorders() {
            map.maxZoomLevel = MapConstants.MAX_ZOOM_LEVEL
            map.minZoomLevel = MapConstants.MIN_ZOOM_LEVEL
            map.isHorizontalMapRepetitionEnabled = false
            map.isVerticalMapRepetitionEnabled = false
            map.setScrollableAreaLimitLatitude(
                MapView.getTileSystem().maxLatitude,
                MapView.getTileSystem().minLatitude, 0
            )
        }

        fun makeTouchable() {
            map.setMultiTouchControls(true)
        }

        fun setMapView() {
            map.controller.setZoom(zoom)
            map.controller.setCenter(centerMapLocation)
        }

        fun setMapListeners() {
            fun setMapCenter(point: IGeoPoint) {
                centerMapLocation = GeoPoint(point)
            }

            fun setCurrentZoom(zoom: Double) {
                this.zoom = zoom
            }

            this.map.setMapListener(DelayedMapListener(object : MapListener {
                override fun onScroll(paramScrollEvent: ScrollEvent): Boolean {
                    setMapCenter(map.mapCenter)
                    drawCircleByRadius()
                    return true
                }

                override fun onZoom(event: ZoomEvent): Boolean {
                    setCurrentZoom(map.zoomLevelDouble)
                    setMapCenter(map.mapCenter)
                    drawCircleByRadius()
                    return false
                }
            }))
        }

        fun configureSearchArea() {
            if (this::searchAreaPolygon.isInitialized && searchAreaPolygon in map.overlays) {
                map.overlays.remove(searchAreaPolygon)
            }
            searchAreaPolygon = Polygon()
            drawCircleByRadius()
            map.overlays.add(this.searchAreaPolygon)
        }

        setScrollBorders()
        setScaleBorders()
        makeTouchable()
        setMapView()
        setMapListeners()
        drawCurrentPlaces()
        configureSearchArea()
    }

    @Composable
    private fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
        remember(mapView) {
            LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    else -> {}
                }
            }
        }

    @Composable
    private fun generateMap(context: Context = LocalContext.current): MapView {
        val mapView = remember {
            MapView(context).apply {
                id = R.id.map
            }
        }

        val lifecycleObserver = rememberMapLifecycleObserver(mapView)
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(lifecycle) {
            lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycle.removeObserver(lifecycleObserver)
            }
        }

        Configuration.getInstance().userAgentValue = context.packageName
        return mapView
    }
}