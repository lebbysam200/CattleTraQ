package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FarmerHomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLocateCow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);

        bLocateCow = (Button) findViewById(R.id.get_location);
        bLocateCow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_location:
                startActivity(new Intent(this,GetCowLocationActivity.class));
                break;
        }
    }
}
