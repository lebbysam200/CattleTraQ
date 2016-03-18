package com.example.batendi.cattletraq;

import android.app.ExpandableListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GetCowLocationActivity extends AppCompatActivity {

    ExpandableListActivity elaCattleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cow_location);

        setTitle("Get Cow Location");

    }
}
