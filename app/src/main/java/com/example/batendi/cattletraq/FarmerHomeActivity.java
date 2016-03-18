package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FarmerHomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLocateCow,bRegCow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);

        setTitle("Farmer Home");

        bRegCow = (Button) findViewById(R.id.register_cow);
        bRegCow.setOnClickListener(this);

        bLocateCow = (Button) findViewById(R.id.get_location);
        bLocateCow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_location:
                startActivity(new Intent(this,GetCowLocationActivity.class));
                break;
            case R.id.register_cow:
                startActivity(new Intent(this,RegisterCowActivity.class));
                break;
        }
    }
}
