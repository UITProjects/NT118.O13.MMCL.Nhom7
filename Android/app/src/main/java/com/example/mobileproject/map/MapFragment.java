package com.example.mobileproject.map;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileproject.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

public class MapFragment extends Fragment {

    View view;
    MapView map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context ctx = view.getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = view.findViewById(R.id.map);
        IMapController mapController = map.getController();
        mapController.setZoom(16.0);
        GeoPoint startPoint = new GeoPoint(10.869905172970164, 106.80345028525176);
        mapController.setCenter(startPoint);
        map = (MapView) view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMinZoomLevel(0.0);
        map.setMaxZoomLevel(19.0);
       // map.getBoundingBox().set(106.79,10.86,106.82,10.88);
        map.setMultiTouchControls(true);
        // RotationGestureOverlay  mRotationGestureOverlay = new RotationGestureOverlay(map);
        //  mRotationGestureOverlay.setEnabled(true);
        // map.getOverlays().add(mRotationGestureOverlay);
        CompassOverlay mCompassOverlay = new CompassOverlay(view.getContext(), new InternalCompassOrientationProvider(view.getContext()), map);
        mCompassOverlay.enableCompass();
        map.getOverlays().add(mCompassOverlay);
    }
}