package com.twoics.geo.map

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.twoics.geo.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.MapView

class Map {
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
        mapView.setMultiTouchControls(true)
        mapView.setScrollableAreaLimitDouble(BoundingBox(85.0, 180.0, -85.0, -180.0))
        mapView.setMaxZoomLevel(20.0)
        mapView.controller.setZoom(4);
        mapView.setMinZoomLevel(4.0)
        mapView.setHorizontalMapRepetitionEnabled(false)
        mapView.setVerticalMapRepetitionEnabled(false)
        mapView.setScrollableAreaLimitLatitude(
            MapView.getTileSystem().maxLatitude,
            MapView.getTileSystem().minLatitude, 0
        )

        return mapView
    }
}