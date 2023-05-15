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
import org.osmdroid.views.overlay.Polygon


private object MapConstants {
    const val WEST_BORDER = -180.0
    const val SOUTH_BORDER = -85.0
    const val NORTH_BORDER = 85.0
    const val EAST_BORDER = 180.0
    const val MAX_ZOOM_LEVEL = 20.0
    const val MIN_ZOOM_LEVEL = 4.0
    const val START_ZOOM = 8.0

    val SEARCH_AREA_COLOR = Color.argb(100, 158, 173, 200)
    val SEARCH_AREA_BORDER_COLOR = Color.argb(180, 158, 173, 200)
}


object Map : IMap {
    private lateinit var map: MapView
    private var areaRadius: Double = 1000.0
    private var foundedPlaces = ArrayList<Bookmark>()
    private var searchAreaPolygon = Polygon()
    override var centerMapLocation: GeoPoint? = GeoPoint(56.0, 92.0)
        private set
    override var zoom: Double = MapConstants.START_ZOOM
        private set

    private fun drawCurrentPlaces() {
        fun drawPlace(bookmark: Bookmark) {
            // TODO
        }

        this.foundedPlaces.forEach {
            drawPlace(bookmark = it)
        }
    }

    override fun drawFoundedPlaces(places: ArrayList<Bookmark>) {
        this.foundedPlaces = places
        drawCurrentPlaces()
    }

    override fun clearPlaces() {
        this.foundedPlaces.clear()
        // TODO
    }

    private fun drawCircleByRadius() {
        this.searchAreaPolygon.points = Polygon.pointsAsCircle(
            GeoPoint(
                this.centerMapLocation!!.latitude,
                this.centerMapLocation!!.longitude
            ),
            this.areaRadius
        )
        this.searchAreaPolygon.fillColor = MapConstants.SEARCH_AREA_COLOR
        this.searchAreaPolygon.strokeColor = MapConstants.SEARCH_AREA_BORDER_COLOR

    }

    override fun drawSearchCircle(radius: Double) {
        this.areaRadius = radius
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
            this.map.setScrollableAreaLimitDouble(
                BoundingBox(
                    MapConstants.NORTH_BORDER,
                    MapConstants.EAST_BORDER,
                    MapConstants.SOUTH_BORDER,
                    MapConstants.WEST_BORDER
                )
            )
        }

        fun setScaleBorders() {
            this.map.setMaxZoomLevel(MapConstants.MAX_ZOOM_LEVEL)
            this.map.setMinZoomLevel(MapConstants.MIN_ZOOM_LEVEL)
            this.map.setHorizontalMapRepetitionEnabled(false)
            this.map.setVerticalMapRepetitionEnabled(false)
            this.map.setScrollableAreaLimitLatitude(
                MapView.getTileSystem().maxLatitude,
                MapView.getTileSystem().minLatitude, 0
            )
        }

        fun makeTouchable() {
            this.map.setMultiTouchControls(true)
        }

        fun setMapView() {
            this.map.controller.setZoom(zoom)
            this.map.controller.setCenter(centerMapLocation)
        }

        fun setMapListeners() {
            fun setMapCenter(point: IGeoPoint) {
                this.centerMapLocation = GeoPoint(point)
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
            if (this.map.overlays.isNotEmpty()) {
                this.map.overlays.clear()
            }
            this.searchAreaPolygon = Polygon()
            drawCircleByRadius()
            this.map.overlays.add(this.searchAreaPolygon)
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

        Configuration.getInstance().setUserAgentValue(context.getPackageName())
        return mapView
    }
}