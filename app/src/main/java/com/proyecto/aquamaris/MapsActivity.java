package com.proyecto.aquamaris;

import androidx.annotation.NonNull;

import android.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.proyecto.aquamaris.databinding.ActivityMapsBinding;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final LatLng defaultCoords = new LatLng(40.4168, -3.7038); // Madrid
    private final float defaultZoom = 5.75f;
    private GeoJsonFeature lastFeature = null;
    private LatLngBounds selectedBounds = null;
    private Marker currentMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.mapToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Habilita la flecha de "volver"
            getSupportActionBar().setDisplayShowHomeEnabled(true);  // Asegúrate de que el ícono de la home se vea
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCoords, defaultZoom));
        currentMarker = mMap.addMarker(new MarkerOptions()
                .position(defaultCoords)
                        .title("Madrid")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        try {
            loadGeoJson();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadGeoJson() throws Exception {
        JSONObject jsonObject = getJsonObject();

        GeoJsonLayer layer = new GeoJsonLayer(mMap, jsonObject);


        for (GeoJsonFeature feature : layer.getFeatures()) {
            GeoJsonPolygonStyle polygonStyle = new GeoJsonPolygonStyle();

            polygonStyle.setStrokeColor(0xEE000000);
            polygonStyle.setStrokeWidth(2.5f);

            feature.setPolygonStyle(polygonStyle);
        }


        layer.setOnFeatureClickListener(feature -> {

            String provincia = feature.getProperty("name");
            Toast.makeText(getApplicationContext(), "Provincia: " + provincia, Toast.LENGTH_SHORT).show();

            if (lastFeature != null && lastFeature.equals(feature))
                abrirDetalleProvincia(provincia);

            lastFeature = (GeoJsonFeature) feature;

            LatLngBounds.Builder boundsBuilder = getBounds(feature);

            selectedBounds = boundsBuilder.build();

            centrarMapa(selectedBounds,provincia);

            mMap.setOnMapClickListener(latLng -> {
                if (selectedBounds != null && !selectedBounds.contains(latLng)) {
                    descentrarMapa();
                    if (currentMarker != null) {
                        currentMarker.setAlpha(0f);
                    }
                }
            });
        });

        layer.addLayerToMap();

    }

    private static LatLngBounds.Builder getBounds(Feature feature) {
        // Obtener los límites de la provincia seleccionada para usar más tarde
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

        if (feature.getGeometry() instanceof GeoJsonPolygon) {
            GeoJsonPolygon polygon = (GeoJsonPolygon) feature.getGeometry();
            for (List<LatLng> coordinates : polygon.getCoordinates()) {
                for (LatLng point : coordinates) {
                    boundsBuilder.include(point);
                }
            }
        } else if (feature.getGeometry() instanceof GeoJsonMultiPolygon) {
            GeoJsonMultiPolygon multiPolygon = (GeoJsonMultiPolygon) feature.getGeometry();
            for (GeoJsonPolygon polygon : multiPolygon.getPolygons()) {
                for (List<LatLng> coordinates : polygon.getCoordinates()) {
                    for (LatLng point : coordinates) {
                        boundsBuilder.include(point);
                    }
                }
            }
        }

        return boundsBuilder;
    }

    private @NonNull JSONObject getJsonObject() throws IOException, JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.spain_provinces2);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder jsonString = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }

        reader.close();
        inputStream.close();

        return new JSONObject(jsonString.toString());
    }

    private void centrarMapa(LatLngBounds bob, String provincia) {
        // Ajustar la cámara para centrar la vista en la provincia seleccionada
        try {
            LatLng center = bob.getCenter();

            currentMarker.setPosition(center);
            currentMarker.setTitle(provincia);
            currentMarker.setAlpha(1f);

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bob, 120),2000,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void descentrarMapa() {
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultCoords, defaultZoom),2000,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirDetalleProvincia(String provincia) {
        Intent intent = new Intent(MapsActivity.this, Consulta.class);
        intent.putExtra("PROVINCIA", provincia.trim());
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}