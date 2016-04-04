package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteCowActivity extends AppCompatActivity {

    String cowRfid;
    Firebase cowRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cow);
        setTitle("Delete Cow");
        Firebase.setAndroidContext(this);

        final String url = "https://flickering-inferno-9581.firebaseio.com/cattle";
        Firebase ref = new Firebase(url);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> cattleList = new ArrayList<String>();
                for (DataSnapshot cow : dataSnapshot.getChildren()) {
                    cowRef = cow.getRef();
                    cowRfid = (String) cow.child("rfid").getValue();
                    cattleList.add(cowRfid);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteCowActivity.this, R.layout.rfid_list, cattleList);
                ListView listView = (ListView) findViewById(R.id.cattle_list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();
                        if (cowRfid.equals(item)){
                            cowRef.push().setValue(null);
                            Toast.makeText(DeleteCowActivity.this, "Cow successfully deleted." + item, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
