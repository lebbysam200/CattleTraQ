package com.example.batendi.cattletraq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class RegisterCowActivity extends AppCompatActivity implements View.OnClickListener{

    Button bCancelReg,bRegCow;
    EditText etRfid, etColor;
    EditText etDob,etMotherRfid;

    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cow);

        Firebase.setAndroidContext(this);

        setTitle("Register Cow");

        ref = new Firebase(getResources().getString(R.string.firebase_url));

        etRfid = (EditText) findViewById(R.id.rfid);
        etColor = (EditText) findViewById(R.id.color);
        etDob = (EditText) findViewById(R.id.dob);
        etMotherRfid = (EditText) findViewById(R.id.motherRfid);

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

                Firebase cattleRef = ref.child("cattle");
                Map<String, String> cattleMap = new HashMap<String, String>();

                cattleMap.put("rfid", rfid);
                cattleMap.put("color", color);
                cattleMap.put("dob", dob);
                cattleMap.put("mother", mother);

                Cow cow = new Cow(rfid,color,dob,mother);
                cattleRef.setValue(cow);

                Toast.makeText(this, "Successfully Added Cow", Toast.LENGTH_LONG).show();

                break;
            case R.id.cancelReg:
                onBackPressed();
                break;
        }
    }

    public boolean cowInDatabase(String rfid){
        Firebase ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/cattle");

        return false;
    }
}
