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

public class ViewCowDetails extends AppCompatActivity {
    String cowRfid;
    String employer,user;
    Firebase ref,ref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cow_details);
        setTitle("View And Edit Cow Details");

        Firebase.setAndroidContext(this);

        final User registerer = new User();

        if(registerer.onlineUserType.equals("farmer")) {
            ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/" + registerer.onlineUser + "/cattle");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> cattleList = new ArrayList<String>();
                    for (DataSnapshot cow : dataSnapshot.getChildren()) {
                        cowRfid = (String) cow.child("rfid").getValue();
                        cattleList.add(cowRfid);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewCowDetails.this, R.layout.rfid_list, cattleList);
                    ListView listView = (ListView) findViewById(R.id.cow_list);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = parent.getItemAtPosition(position).toString();
                            Cow mycow = new Cow();
                            mycow.rfid = item;
                            startActivity(new Intent(ViewCowDetails.this, CowDetails.class));
                        }
                    });
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }else{
            ref1 = new Firebase("https://flickering-inferno-9581.firebaseio.com/users");
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot user1 : dataSnapshot.getChildren()) {
                        user = (String) user1.child("username").getValue();
                        if (registerer.onlineUser.equals(user.replace(".com",""))) {
                            employer = (String) user1.child("employer").getValue();
                            ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/"+employer+"/cattle");
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    List<String> cattleList = new ArrayList<String>();
                                    for (DataSnapshot cow : dataSnapshot.getChildren()) {
                                        cowRfid = (String) cow.child("rfid").getValue();
                                        cattleList.add(cowRfid);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewCowDetails.this, R.layout.rfid_list, cattleList);
                                    ListView listView = (ListView) findViewById(R.id.cow_list);
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String item = parent.getItemAtPosition(position).toString();
                                            Cow mycow = new Cow();
                                            mycow.rfid = item;
                                            startActivity(new Intent(ViewCowDetails.this, CowDetails.class));
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                }
                            });
                            break;
                        }
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }

    }
}
