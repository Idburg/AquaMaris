package com.proyecto.aquamaris;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.proyecto.aquamaris.databinding.ActivityMapsBinding;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final LatLng defaultCoords = new LatLng(40.4168, -3.7038); // Madrid
    private final float defaultZoom = 5.75f;
    private GeoJsonFeature selectedFeature = null;
    private LatLngBounds selectedBounds = null;

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCoords, defaultZoom));

        loadGeoJson();
    }

    private void loadGeoJson() {
        boolean alreadyClicked = false;
        float currentZoom = defaultZoom;

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

            layer.setOnFeatureClickListener(feature -> {
                selectedFeature = (GeoJsonFeature) feature;

                String provincia = feature.getProperty("name");
                Toast.makeText(getApplicationContext(), "Provincia: " + provincia, Toast.LENGTH_SHORT).show();

                LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                selectedBounds = boundsBuilder.build();

                if (feature.getGeometry() instanceof GeoJsonPolygon) {
                    GeoJsonPolygon polygon = (GeoJsonPolygon) feature.getGeometry();


                    // Recorrer todas las coordenadas del polígono
                    for (List<LatLng> coordinates : polygon.getCoordinates()) {
                        for (LatLng point : coordinates) {
                            boundsBuilder.include(point);
                        }
                    }

                }
                else if (feature.getGeometry() instanceof GeoJsonMultiPolygon) {
                    GeoJsonMultiPolygon multiPolygon = (GeoJsonMultiPolygon) feature.getGeometry();

                    // Recorrer cada polígono en el multipolígono
                    for (GeoJsonPolygon polygon : multiPolygon.getPolygons()) {
                        for (List<LatLng> coordinates : polygon.getCoordinates()) {
                            for (LatLng point : coordinates) {
                                boundsBuilder.include(point);
                            }
                        }
                    }
                }

                centrarMapa(boundsBuilder);

                Intent intent = new Intent(this, Consulta.class);
                intent.putExtra("PROVINCIA",provincia);
                startActivity(intent);

            });

            layer.addLayerToMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void centrarMapa(LatLngBounds.Builder bob) {
        // Ajustar la cámara para centrar la vista en la provincia seleccionada
        try {
            LatLngBounds bounds = bob.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void descentrarMapa(LatLngBounds.Builder bob) {
        try {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCoords, defaultZoom));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}