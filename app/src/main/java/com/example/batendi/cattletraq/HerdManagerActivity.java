package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HerdManagerActivity extends AppCompatActivity implements View.OnClickListener{
    Button bRegCow,bLogKraal,bLogRelease,bLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herd_manager);

        setTitle("Herd Manager Home");

        bRegCow = (Button) findViewById(R.id.reg_cow);
        bRegCow.setOnClickListener(this);

        bLogKraal = (Button) findViewById(R.id.kraal_time);
        bLogKraal.setOnClickListener(this);

        bLogRelease = (Button) findViewById(R.id.release_time);
        bLogRelease.setOnClickListener(this);

        bLogout = (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_cow:
                startActivity(new Intent(this,RegisterCowActivity.class));
                break;
            case R.id.logout:
                onBackPressed();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Please Login to Continue", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
