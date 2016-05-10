package com.example.batendi.cattletraq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CowDetails extends AppCompatActivity implements View.OnClickListener{

    Button bCancel,bSave;
    EditText etRfid, etColor;
    EditText etMotherRfid,etKraalLocation;
    EditText etDob,etCowName;

    Firebase cowRef,ref,ref1;
    String cowRfid,user,dob,color,motherRFID,kraalLoc,cowname;
    List<String> cattleList;
    String employer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow_details);
        Firebase.setAndroidContext(this);

        setTitle("Cow Details");
        final User registerer = new User();
        final Cow c = new Cow();

        etRfid = (EditText) findViewById(R.id.rfid);
        etColor = (EditText) findViewById(R.id.color);
        etDob = (EditText) findViewById(R.id.dob);
        etMotherRfid = (EditText) findViewById(R.id.motherRfid);
        etKraalLocation = (EditText) findViewById(R.id.kraalLoc);
        etCowName = (EditText) findViewById(R.id.cowName);

        if(registerer.onlineUserType.equals("farmer")){
            ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/"+registerer.onlineUser+"/cattle");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cattleList = new ArrayList<String>();
                    for (DataSnapshot cow : dataSnapshot.getChildren()) {
                        cowRfid = (String) cow.child("rfid").getValue();
                        if(c.rfid.equals(cowRfid)) {
                            dob = (String) cow.child("dob").getValue();
                            color = (String) cow.child("color").getValue();
                            motherRFID = (String) cow.child("mother").getValue();
                            kraalLoc = (String) cow.child("kraal location").getValue();
                            cowname = (String) cow.child("cow name").getValue();

                            etRfid.setText(cowRfid);
                            etColor.setText(color);
                            etDob.setText(dob);
                            etDob.setEnabled(false);
                            etMotherRfid.setText(motherRFID);
                            etKraalLocation.setText(kraalLoc);
                            etCowName.setText(cowname);

                            cowRef = cow.getRef();
                            cattleList.add(cowRfid);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }else{
            ref1 = new Firebase("https://flickering-inferno-9581.firebaseio.com/users");
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot user1 : dataSnapshot.getChildren()) {
                        user = (String) user1.child("username").getValue();
                        if (registerer.onlineUser.equals(user.replace(".com",""))) {
                            employer = (String) user1.child("employer").getValue();
                            ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/"+employer+"/cattle");
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    cattleList = new ArrayList<String>();
                                    for (DataSnapshot cow : dataSnapshot.getChildren()) {
                                        cowRfid = (String) cow.child("rfid").getValue();
                                        if(c.rfid.equals(cowRfid)) {
                                            dob = (String) cow.child("dob").getValue();
                                            color = (String) cow.child("color").getValue();
                                            motherRFID = (String) cow.child("mother").getValue();
                                            kraalLoc = (String) cow.child("kraal location").getValue();
                                            cowname = (String) cow.child("cow name").getValue();

                                            etRfid.setText(cowRfid);
                                            etColor.setText(color);
                                            etDob.setText(dob);
                                            etDob.setEnabled(false);
                                            etMotherRfid.setText(motherRFID);
                                            etKraalLocation.setText(kraalLoc);
                                            etCowName.setText(cowname);

                                            cowRef = cow.getRef();
                                            cattleList.add(cowRfid);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                }
                            });
                            break;
                        }
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });

        }

        bSave = (Button) findViewById(R.id.save);
        bCancel = (Button) findViewById(R.id.cancelSave);

        bSave.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.save:
                String rfid = etRfid.getText().toString();
                String color = etColor.getText().toString();
                String mother = etMotherRfid.getText().toString();
                String kraal = etKraalLocation.getText().toString();
                String dob = etDob.getText().toString();
                String cowName = etCowName.getText().toString();

                if (!rfid.equals("")) {
                    Map<String, String> cattleMap = new HashMap<String, String>();
                    if(validateString(rfid)==true){
                        Toast.makeText(this, "Please enter a valid rfid", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                    if(validateString(color)==false){
                        Toast.makeText(this, "Please enter a valid color", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                    if(validateString(mother)==true){
                        Toast.makeText(this, "Please enter a valid mother Rfid", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                    if(validateString(kraal)==false){
                        Toast.makeText(this, "Please enter a valid location name", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                    if(validateString(cowName)==false){
                        Toast.makeText(this, "Please enter a valid cow name", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }else if(!validateString(rfid)&&validateString(kraal)&&!validateString(mother)&&validateString(color)&&validateString(cowName)){
                        cattleMap.put("rfid", rfid);
                        cattleMap.put("color", color);
                        cattleMap.put("dob", dob);
                        cattleMap.put("mother", mother);
                        cattleMap.put("kraal location", kraal);
                        cattleMap.put("cow name",cowName);

                        cowRef.setValue(cattleMap);

                        Toast.makeText(this, "Cow Details Updated", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }

                }
                else if(rfid.equals("")) {
                    Toast.makeText(this, "Please Enter Cow RFID", Toast.LENGTH_LONG).show();
                }
                else if(cattleList.contains(rfid)){
                    Toast.makeText(this, "Cow already exists", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.cancelSave:
                onBackPressed();
                break;
        }
    }

    public boolean validateString(String string){
        boolean isString = true;
        try {
            int num = Integer.parseInt(string);
            isString = false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return isString;
    }
}
