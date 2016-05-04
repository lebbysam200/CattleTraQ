package com.example.batendi.cattletraq;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

public class LocateAll extends AppCompatActivity {

    MyItemizedOverlay myItemizedOverlay = null;
    double latitude;
    double longitude;
    String kraalLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_all);
        setTitle("All Cattle");
        final User registerer = new User();
        Firebase ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/"+registerer.onlineUser+"/cattle");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot cow : dataSnapshot.getChildren()) {
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
                                    MapView mapView = (MapView) findViewById(R.id.mapview1);
                                    Drawable marker = getResources().getDrawable(android.R.drawable.star_on);
                                    int markerWidth = marker.getIntrinsicWidth();
                                    int markerHeight = marker.getIntrinsicHeight();
                                    marker.setBounds(0, markerHeight, markerWidth, 0);

                                    ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
                                    IMapController mapController = mapView.getController();
                                    mapController.setCenter(new GeoPoint(-22.3285, 24.6849));

                                    myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
                                    mapView.getOverlays().add(myItemizedOverlay);

                                    GeoPoint loc = new GeoPoint(latitude, longitude);
                                    GeoPoint loc1 = new GeoPoint(-23.6719, 22.7927);
                                    GeoPoint loc2 = new GeoPoint(-23.1078, 26.8321);
                                    GeoPoint loc3 = new GeoPoint(-24.633, 25.867);
                                    GeoPoint loc4 = new GeoPoint(-23.6719, 22.7927);
                                    GeoPoint loc5 = new GeoPoint(-24.9881, 25.3430);
                                    GeoPoint loc6 = new GeoPoint(-24.6641, 25.7836);
                                    GeoPoint loc7 = new GeoPoint(-24.6060, 24.7202);
                                    myItemizedOverlay.addItem(loc,"","");
                                    myItemizedOverlay.addItem(loc1,"","");
                                    myItemizedOverlay.addItem(loc2,"","");
                                    myItemizedOverlay.addItem(loc3,"","");
                                    myItemizedOverlay.addItem(loc4,"","");
                                    myItemizedOverlay.addItem(loc5,"","");
                                    myItemizedOverlay.addItem(loc6,"","");
                                    myItemizedOverlay.addItem(loc7,"","");



                                }
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

}
