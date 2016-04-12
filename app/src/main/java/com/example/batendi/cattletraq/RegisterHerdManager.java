package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterHerdManager extends AppCompatActivity implements View.OnClickListener{

    Button cancel,reg;
    EditText etName, etUsername;
    EditText etPass,etPassConf,etKraal;

    Firebase ref,ref1;
    List<String> managerList,userList;
    String manager,user;
    User u = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_herd_manager);

        setTitle("Register Herd Manager");


        ref = new  Firebase("https://flickering-inferno-9581.firebaseio.com/"+u.onlineUser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                managerList = new ArrayList<String>();
                for (DataSnapshot manager1 : dataSnapshot.getChildren()) {
                    manager = (String) manager1.child("username").getValue();
                    managerList.add(manager);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        ref1 = new Firebase("https://flickering-inferno-9581.firebaseio.com/users");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList = new ArrayList<String>();
                for (DataSnapshot user1 : dataSnapshot.getChildren()) {
                    user = (String) user1.child("username").getValue();
                    userList.add(user);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        etName = (EditText) findViewById(R.id.hName);
        etUsername =(EditText) findViewById(R.id.hUsername);
        etPass = (EditText) findViewById(R.id.password);
        etPassConf = (EditText) findViewById(R.id.pass_conf);
        etKraal = (EditText) findViewById(R.id.krallLoc);

        reg = (Button) findViewById(R.id.reg);
        reg.setOnClickListener(this);

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                onBackPressed();
                break;
            case R.id.reg:
                Map<String, String> userMap = new HashMap<String, String>();

                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                String conf = etPassConf.getText().toString();

                if (pass.equals(conf) && !managerList.contains(username) && validateString(name)&&!userList.contains(username)) {

                    userMap.put("name", name);
                    userMap.put("username", username);
                    userMap.put("type", "herdmanager");
                    userMap.put("employer",u.onlineUser);

                    Map<String, String> user1 = new HashMap<String, String>();
                    user1.put("manager",username);

                    User user = new User(username, name, pass);
                    ref1.push().setValue(userMap);
                    ref.push().setValue(user1);

                    Map<String, Map<String, String>> users = new HashMap<String, Map<String, String>>();
                    users.put("user", userMap);
                    if (!username.endsWith(".com") && !username.contains("@")) {
                        Toast.makeText(this, "Invalid email, please try again", Toast.LENGTH_LONG).show();
                    } else {

                        ref1.createUser(username, pass, new Firebase.ResultHandler() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(RegisterHerdManager.this, "Successfully created user", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(RegisterHerdManager.this, "Registration Failed, Please Try Again", Toast.LENGTH_LONG).show();
                            }
                        });
                        RegisterHerdManager.this.finish();
                        startActivity(new Intent(this, LoginActivity.class));
                    }

                } else if (!validateString(name)) {
                    Toast.makeText(this, "Please Enter a valid name", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                } else if (managerList.contains(username)) {
                    Toast.makeText(this, "Manager already exists", Toast.LENGTH_LONG).show();
                }else if (userList.contains(username)) {
                    Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Passwords Do Not Match, Try Again", Toast.LENGTH_LONG).show();
                }

                break;
        }

    }

    public boolean validateString(String string){
        boolean isString = true;
        try {
            int num = Integer.parseInt(string);
            isString = false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return isString;
    }
}
