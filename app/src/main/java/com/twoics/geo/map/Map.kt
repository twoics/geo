package com.twoics.geo.map

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.twoics.geo.R
import com.twoics.geo.api.PlacesResponse
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
    const val DETAIL_ZOOM = 14.0

    val SEARCH_AREA_COLOR = Color.argb(100, 158, 173, 200)
    val SEARCH_AREA_BORDER_COLOR = Color.argb(180, 158, 173, 200)
}

private data class MapMarker(
    val place: PlacesResponse,
    val marker: Marker
)

class Map(
    defaultAreaRadius: Double,
    defaultMapLocation: GeoPoint,
    private val mapDataTransfer: IMapDataTransfer
) : IMap {
    private lateinit var map: MapView
    private lateinit var searchAreaPolygon: Polygon

    private var foundedPlaces = ArrayList<MapMarker>()
    private var zoom: Double = MapConstants.START_ZOOM

    override var areaRadius: Double = defaultAreaRadius

    override var centerMapLocation: GeoPoint = defaultMapLocation
        private set

    override var isShowSearchArea = true
        private set

    private fun drawCurrentPlaces() {
        fun addMarkerClickEvent(mapMarker: MapMarker) {
            val marker = mapMarker.marker
            val place = mapMarker.place

            marker.setOnMarkerClickListener { _, _ ->
                mapDataTransfer.sendEvent(MapEvent.PeakPlace(place))
                true
            }
        }

        fun drawPlace(mapMarker: MapMarker) {
            fun setPlaceIcon(mapMarker: MapMarker) {
                val context = map.context
                val place = mapMarker.place
//                TODO
//                val icon = PlaceIcons.getMapIcon(place, context)
//                mapMarker.marker.icon = icon
//                mapMarker.marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            }

            val marker = mapMarker.marker
            val bookmark = mapMarker.place

            marker.position = GeoPoint(bookmark.lat, bookmark.long)
            addMarkerClickEvent(mapMarker)
            setPlaceIcon(mapMarker)
            map.overlays.add(marker);
        }

        foundedPlaces.forEach {
            drawPlace(mapMarker = it)
        }

        Log.d("FOUNDED PLACES", foundedPlaces.toString())
        map.invalidate()
    }

    override fun drawFoundedPlaces(places: ArrayList<PlacesResponse>) {
        clearPlaces()
        places.forEach {
            foundedPlaces.add(
                MapMarker(
                    place = it,
                    marker = Marker(map)
                )
            )
        }
        drawCurrentPlaces()
    }

    override fun clearPlaces() {
        foundedPlaces.forEach {
            map.overlays.remove(it.marker)
        }
        foundedPlaces.clear()
        map.invalidate()
    }

    override fun focusedDrawBookmark(place: Bookmark) {
        // TODO
//        fun moveMapToPlace() {
//            centerMapLocation = GeoPoint(place.lat, place.long)
//            zoom = MapConstants.DETAIL_ZOOM
//            map.controller.setCenter(centerMapLocation)
//            map.controller.setZoom(MapConstants.DETAIL_ZOOM)
//        }
//
//        clearPlaces()
//        foundedPlaces.add(
//            MapMarker(
//                place = place,
//                marker = Marker(map)
//            )
//        )
//
//        moveMapToPlace()
//
//        drawCurrentPlaces()
//        drawCircleByRadius()
    }

    override fun showSearchArea(boolean: Boolean) {
        isShowSearchArea = boolean
        searchAreaPolygon.isVisible = boolean
        map.invalidate()
    }

    private fun drawCircleByRadius() {
        searchAreaPolygon.points = Polygon.pointsAsCircle(
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
        if (this.foundedPlaces.isNotEmpty())
            Log.d("Mark", this.foundedPlaces.first().marker.toString())
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
            map.overlays.add(searchAreaPolygon)
        }

        fun setSearchAreaVisibility() {
            searchAreaPolygon.isVisible = isShowSearchArea
        }

        setScrollBorders()
        setScaleBorders()
        makeTouchable()
        setMapView()
        setMapListeners()
        configureSearchArea()
        setSearchAreaVisibility()
        if (foundedPlaces.isNotEmpty()) {
            val places = arrayListOf<PlacesResponse>()
            foundedPlaces.forEach {
                places.add(it.place)
            }
            drawFoundedPlaces(places)
        } else {
            drawCurrentPlaces()
        }
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