package com.example.batendi.cattletraq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteCowActivity extends AppCompatActivity {

    String cowRfid,item;
    Firebase cowRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cow);
        setTitle("Delete Cow");
        Firebase.setAndroidContext(this);

        final User registerer = new User();
        final String url = "https://flickering-inferno-9581.firebaseio.com/"+registerer.onlineUser+"/cattle";
        Firebase ref = new Firebase(url);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> cattleList = new ArrayList<String>();
                for (DataSnapshot cow : dataSnapshot.getChildren()) {
                    cowRef = cow.getRef();
                    cowRfid = (String) cow.child("cow name").getValue();
                    cattleList.add(cowRfid);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteCowActivity.this, R.layout.rfid_list, cattleList);
                ListView listView = (ListView) findViewById(R.id.rfid_list1);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView itemClicked = (TextView) view;
                        item = itemClicked.getText().toString();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeleteCowActivity.this);
                        alertDialog.setTitle("Delete Cow Confirm");
                        alertDialog.setMessage("Delete " + item + "?");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cowRef.removeValue();
                            Toast.makeText(DeleteCowActivity.this, "Cow successfully deleted." + item, Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DeleteCowActivity.this, "Cancelled.", Toast.LENGTH_SHORT).show();
                        }
                    });
                        alertDialog.show();

                    }
                });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
