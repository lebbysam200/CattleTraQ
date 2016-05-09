package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DateActivity extends AppCompatActivity implements View.OnClickListener{

    DatePicker datePicker;
    Button bSet;
    private int myear;
    private int mmonth;
    private int mday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        datePicker = (DatePicker) findViewById(R.id.date);
        setTitle("Cow Date of Birth");

        setCurrentDateOnView();

        bSet = (Button) findViewById(R.id.set);
        bSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set:
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year = datePicker.getYear();

                if (month < mmonth && year == myear)
                {
                    Cow cow = new Cow();
                    cow.birthdate = day + "/" + month + "/" + year;
                    startActivity(new Intent(this, RegisterCowActivity.class));
                    finish();
                }

                else if (day <= mday && year == myear && month == mmonth)
                {
                    Cow cow = new Cow();
                    cow.birthdate = day + "/" + month + "/" + year;
                    startActivity(new Intent(this, RegisterCowActivity.class));
                    finish();
                }
                else {

                    Toast.makeText(DateActivity.this,"Please Pick a Date not After Today",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH)+1;
        mday = c.get(Calendar.DAY_OF_MONTH);
    }
}
