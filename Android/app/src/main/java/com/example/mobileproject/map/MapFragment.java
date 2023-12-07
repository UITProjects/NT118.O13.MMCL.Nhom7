package com.example.mobileproject.map;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.mobileproject.CustomRequest;
import com.example.mobileproject.Dashboard;
import com.example.mobileproject.R;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment {

    View view;
    MapView map;
    Handler ui_handler = new Handler();

    void showMarkerDialog(View current_view,String title){


        Dialog marker_dialog = new Dialog(current_view.getContext());
        marker_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        marker_dialog.setContentView(R.layout.bottomsheetlayout_marker);
        marker_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        marker_dialog.getWindow().setGravity(Gravity.BOTTOM);
        marker_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        TextView title_textview = marker_dialog.findViewById(R.id.txtview_title);
        TextView humidity_value = marker_dialog.findViewById(R.id.txtview_humidity_value);
        humidity_value.append("99");
        title_textview.setText(title);


        marker_dialog.show();

    }

    Thread weather_asset_thread = new Thread(new Runnable() {
        Map<String,String> header = new HashMap<>();


        @Override
        public void run() {
            header.put("Authorization", "Bearer ".concat(Dashboard.token));
            header.put("accept","application/json");
            try {
                AssetApi request = new AssetApi(
                        "https://uiot.ixxc.dev/api/master/asset/5zI6XqkQVSfdgOrZ1MyWEf",
                        "GET",
                        Dashboard.token
                );
                Map<String,String> asset_data = request.GeData();


                int stop = 0;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    });








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        Context ctx = view.getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        map = view.findViewById(R.id.map);
        map.setMultiTouchControls(true);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMinZoomLevel(0.0);
        map.setMaxZoomLevel(19.0);
        weather_asset_thread.start();


        IMapController mapController = map.getController();
        mapController.setZoom(19.0);
        GeoPoint startPoint = new GeoPoint(10.869905172970164, 106.80345028525176);
        mapController.setCenter(startPoint);


         RotationGestureOverlay  mRotationGestureOverlay = new RotationGestureOverlay(map);
         mRotationGestureOverlay.setEnabled(true);
         map.getOverlays().add(mRotationGestureOverlay);


        CompassOverlay mCompassOverlay = new CompassOverlay(view.getContext(), new InternalCompassOrientationProvider(view.getContext()), map);
        mCompassOverlay.enableCompass();
        map.getOverlays().add(mCompassOverlay);




        Marker defaultweather_marker = new Marker(map);
        int a = R.drawable.weather_partly_cloudy;
        Resources res = getResources();
        Drawable icon =  res.getDrawable(R.drawable.weather_partly_cloudy, getContext().getTheme());

        defaultweather_marker.setImage(icon);
        defaultweather_marker.setTitle("Default weather");
        defaultweather_marker.setPosition(new GeoPoint(10.869778736885038,106.80280655508835));
        defaultweather_marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        defaultweather_marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                showMarkerDialog(mapView,marker.getTitle());
                return false;
            }
        });




        map.getOverlays().add(defaultweather_marker);
        map.invalidate();


        try {
            weather_asset_thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return view;
    }


}