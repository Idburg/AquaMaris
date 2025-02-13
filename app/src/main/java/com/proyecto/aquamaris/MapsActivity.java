package com.proyecto.aquamaris;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.proyecto.aquamaris.databinding.ActivityMapsBinding;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng spainCenter = new LatLng(40.4168, -3.7038); // Madrid
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spainCenter, 5.75f));

        loadGeoJson();
    }

    private void loadGeoJson() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.spain_provinces);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            reader.close();
            inputStream.close();

            JSONObject jsonObject = new JSONObject(jsonString.toString());

            GeoJsonLayer layer = new GeoJsonLayer(mMap, jsonObject);

            for (GeoJsonFeature feature : layer.getFeatures()) {
                GeoJsonPolygonStyle polygonStyle = new GeoJsonPolygonStyle();

                polygonStyle.setStrokeColor(0xEE000000);
                polygonStyle.setStrokeWidth(2.5f);

                // Aplicar estilo a la provincia
                feature.setPolygonStyle(polygonStyle);
            }

            layer.addLayerToMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}