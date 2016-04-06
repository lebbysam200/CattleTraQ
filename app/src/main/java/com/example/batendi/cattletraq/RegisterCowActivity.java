package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterCowActivity extends AppCompatActivity implements View.OnClickListener{

    Button bCancelReg,bRegCow;
    EditText etRfid, etColor;
    EditText etDob,etMotherRfid,etKraalLocation;

    Firebase ref;
    String cowRfid;
    List<String> cattleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cow);
        Firebase.setAndroidContext(this);

        setTitle("Register Cow");

        ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/cattle");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cattleList = new ArrayList<String>();
                for (DataSnapshot cow : dataSnapshot.getChildren()) {
                    cowRfid = (String) cow.child("rfid").getValue();
                    cattleList.add(cowRfid);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        etRfid = (EditText) findViewById(R.id.rfid);
        etColor = (EditText) findViewById(R.id.color);
        etDob = (EditText) findViewById(R.id.dob);
        etMotherRfid = (EditText) findViewById(R.id.motherRfid);
        etKraalLocation = (EditText) findViewById(R.id.kraalLoc);

        bRegCow = (Button) findViewById(R.id.regCow);
        bCancelReg = (Button) findViewById(R.id.cancelReg);

        bRegCow.setOnClickListener(this);
        bCancelReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.regCow:
                String rfid = etRfid.getText().toString();
                String color = etColor.getText().toString();
                String dob = etDob.getText().toString();
                String mother = etMotherRfid.getText().toString();
                String kraal = etKraalLocation.getText().toString();

                if (!rfid.equals("")&& !cattleList.contains(rfid)) {
                    Map<String, String> cattleMap = new HashMap<String, String>();

                    cattleMap.put("rfid", rfid);
                    cattleMap.put("color", color);
                    cattleMap.put("dob", dob);
                    cattleMap.put("mother", mother);
                    cattleMap.put("kraal location",kraal);

                    ref.push().setValue(cattleMap);

                    Toast.makeText(this, "Successfully Added Cow", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                else if(rfid.equals("")) {
                    Toast.makeText(this, "Please Enter Cow RFID", Toast.LENGTH_LONG).show();
                }
                else if(cattleList.contains(rfid)){
                    Toast.makeText(this, "Cow already exists", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.cancelReg:
                onBackPressed();
                break;
        }
    }
}
