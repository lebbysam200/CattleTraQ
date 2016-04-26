package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HerdManagerActivity extends AppCompatActivity implements View.OnClickListener{
    Button bRegCow,bLogKraal,bLogRelease,bLogout,bEditDetails;
    Firebase ref,ref1;
    String user,employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herd_manager);

        setTitle("Herd Manager Home");
        Firebase.setAndroidContext(this);

        final User registerer = new User();
        ref1 = new Firebase("https://flickering-inferno-9581.firebaseio.com/users");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user1 : dataSnapshot.getChildren()) {
                    user = (String) user1.child("username").getValue();
                    if (registerer.onlineUser.equals(user.replace(".com", ""))) {
                        employer = (String) user1.child("employer").getValue();
                        ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/" + employer);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        bRegCow = (Button) findViewById(R.id.reg_cow);
        bRegCow.setOnClickListener(this);

        bLogKraal = (Button) findViewById(R.id.kraal_time);
        bLogKraal.setOnClickListener(this);

        bLogRelease = (Button) findViewById(R.id.release_time);
        bLogRelease.setOnClickListener(this);

        bLogout = (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(this);

        bEditDetails = (Button) findViewById(R.id.viewDetails);
        bEditDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_cow:
                startActivity(new Intent(this,DateActivity.class));
                break;
            case R.id.logout:
                onBackPressed();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Please Login to Continue", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewDetails:
                startActivity(new Intent(this,ViewCowDetails.class));
                break;
            case R.id.release_time:
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Firebase releaseTimes = ref.child("releaseTimes");
                Map<String, String> times = new HashMap<String, String>();
                times.put("release time",currentDateTimeString);
                releaseTimes.push().setValue(times);

                Toast.makeText(this, "Released at "+currentDateTimeString, Toast.LENGTH_SHORT).show();
                break;
            case R.id.kraal_time:
                String currentTime = DateFormat.getDateTimeInstance().format(new Date());
                Firebase kraalTimes = ref.child("kraalTimes");
                Map<String, String> time = new HashMap<String, String>();
                time.put("kraal time",currentTime);
                kraalTimes.push().setValue(time);

                Toast.makeText(this, "Kraaled at "+currentTime, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
