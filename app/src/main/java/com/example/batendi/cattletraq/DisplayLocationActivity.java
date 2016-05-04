package com.example.batendi.cattletraq;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;

import android.graphics.drawable.Drawable;;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DisplayLocationActivity extends AppCompatActivity implements View.OnClickListener {

    MyItemizedOverlay myItemizedOverlay = null;
    double latitude;
    double longitude;
    String kraalLocation;
    Button path;
    String json = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_location);
        path = (Button) findViewById(R.id.getPath);
        path.setOnClickListener(this);
        Firebase.setAndroidContext(this);
        getKraalLocation();

    }

    public void getKraalLocation(){
        final User registerer = new User();
        Firebase ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/"+registerer.onlineUser+"/cattle");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cow : dataSnapshot.getChildren()) {
                    String rfid = (String) cow.child("rfid").getValue();
                    Cow mycow = new Cow();
                    String cowRfid = mycow.rfid;
                    if (rfid.equals(cowRfid)) {
                        kraalLocation = (String) cow.child("kraal location").getValue();
                        Firebase ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/cities");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot city : dataSnapshot.getChildren()) {
                                    String city1 = (String) city.child("name").getValue();

                                    if (kraalLocation.equals(city1)) {
                                        String latitude1 = city.child("latitude").getValue().toString();
                                        String longitude1 = (String) city.child("longitude").getValue();
                                        latitude = Double.parseDouble(latitude1);
                                        longitude = Double.parseDouble(longitude1);
                                        final GeoPoint DEFAULT_LOCATION = new GeoPoint(latitude, longitude);

                                        MapView mapView = (MapView) findViewById(R.id.mapview);
                                        mapView.setClickable(true);
                                        mapView.setBuiltInZoomControls(true);
                                        mapView.setMultiTouchControls(true);
                                        mapView.setUseDataConnection(true);
                                        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);

                                        IMapController mapViewController = mapView.getController();
                                        mapViewController.setZoom(15);
                                        mapViewController.setCenter(DEFAULT_LOCATION);
                                        setTitle("Cow Location");

                                        Drawable marker = getResources().getDrawable(android.R.drawable.star_big_on);
                                        int markerWidth = marker.getIntrinsicWidth();
                                        int markerHeight = marker.getIntrinsicHeight();
                                        marker.setBounds(0, markerHeight, markerWidth, 0);

                                        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

                                        myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
                                        mapView.getOverlays().add(myItemizedOverlay);

                                        GeoPoint loc = new GeoPoint(latitude, longitude);
                                        myItemizedOverlay.addItem(loc, "loc", "loc");

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getPath:
                PathOverlay myPath = new PathOverlay(Color.RED, this);
                MapView mapView = (MapView) findViewById(R.id.mapview);
                IMapController mapController = mapView.getController();
                try {
                    InputStream is = this.getAssets().open("coordinates.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    json = new String(buffer, "UTF-8");
                    JSONObject obj = new JSONObject(json);
                    JSONArray m_jArry = obj.getJSONArray("coordinates");

                    for (int i = 0; i < m_jArry.length(); i++) {
                        JSONObject jo_inside = m_jArry.getJSONObject(i);
                        double lat = jo_inside.getDouble("latitude");
                        double lng = jo_inside.getDouble("longitude");


                        GeoPoint gPnt0 = new GeoPoint(-lat, lng);
                        myPath.addPoint(gPnt0);

                    }
                    mapView.setTileSource(TileSourceFactory.MAPNIK);
                    mapView.setBuiltInZoomControls(true);
                    mapView.setMultiTouchControls(true);

                    mapController.setCenter(new GeoPoint(-22.3285, 24.6849));
                    mapController.setZoom(15);
                    mapView.getOverlays().add(myPath);
                } catch (IOException ex) {
                    Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                    //return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        }

}
