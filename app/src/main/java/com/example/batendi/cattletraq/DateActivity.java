package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class DateActivity extends AppCompatActivity implements View.OnClickListener{

    DatePicker datePicker;
    Button bSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        datePicker = (DatePicker) findViewById(R.id.date);

        bSet = (Button) findViewById(R.id.set);
        bSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set:
                String day = ""+datePicker.getDayOfMonth();
                String month = "/"+datePicker.getMonth();
                String year = "/"+datePicker.getYear();
                Cow cow = new Cow();
                cow.birthdate = day+month+year;
                startActivity(new Intent(this,RegisterCowActivity.class));
                finish();
                break;
        }
    }
}
