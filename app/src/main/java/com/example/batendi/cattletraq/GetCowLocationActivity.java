package com.example.batendi.cattletraq;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetCowLocationActivity extends AppCompatActivity {

    String cowRfid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cow_location);
        setTitle("Get Cow Location");

        Firebase.setAndroidContext(this);

        final User registerer = new User();
        Firebase ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/"+registerer.onlineUser+"/cattle");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> cattleList = new ArrayList<String>();
                for (DataSnapshot cow : dataSnapshot.getChildren()) {
                    cowRfid = (String) cow.child("rfid").getValue();
                    cattleList.add(cowRfid);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(GetCowLocationActivity.this, R.layout.rfid_list, cattleList);
                ListView listView = (ListView) findViewById(R.id.cattle_list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();
                        Cow mycow = new Cow();
                        mycow.rfid = item;
                        Toast.makeText(GetCowLocationActivity.this, "Locate"+ item, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GetCowLocationActivity.this, DisplayLocationActivity.class));
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }
}
