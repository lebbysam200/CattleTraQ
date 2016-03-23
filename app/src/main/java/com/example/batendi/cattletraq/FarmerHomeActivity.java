package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FarmerHomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLocateCow,bRegCow,bLogout,bDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);

        setTitle("Farmer Home");

        bRegCow = (Button) findViewById(R.id.register_cow);
        bRegCow.setOnClickListener(this);

        bLocateCow = (Button) findViewById(R.id.get_location);
        bLocateCow.setOnClickListener(this);

        bLogout = (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(this);

        bDelete = (Button) findViewById(R.id.delete_cow);
        bDelete.setOnClickListener(this);

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
            case R.id.logout:
                onBackPressed();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Please Login to Continue" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_cow:
                break;

        }
    }


}
