package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterFarmerActivity extends AppCompatActivity implements View.OnClickListener{
    Button cancel,reg;
    EditText etName, etUsername;
    EditText etPass,etPassConf,etKraal;

    Firebase ref,ref1;
    List<String> userList;
    String user,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farmer);

        Firebase.setAndroidContext(this);

        setTitle("Register As Farmer");

        ref = new Firebase("https://flickering-inferno-9581.firebaseio.com/users");
        ref.addValueEventListener(new ValueEventListener() {
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

        etName = (EditText) findViewById(R.id.name);
        etUsername =(EditText) findViewById(R.id.username);
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
                username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                String conf = etPassConf.getText().toString();

                if(pass.equals(conf) && !userList.contains(username)&& validateString(name)) {

                    userMap.put("name", name);
                    userMap.put("username", username);
                    userMap.put("type", "farmer");

                    User user = new User(username,name,pass);
                    ref.push().setValue(userMap);


                    Map<String, Map<String, String>> users = new HashMap<String, Map<String, String>>();
                    users.put("user", userMap);
                    if(!username.endsWith(".com") && !username.contains("@")){
                        Toast.makeText(this, "Invalid email, please try again", Toast.LENGTH_LONG).show();
                    }else {

                        ref.createUser(username, pass, new Firebase.ResultHandler() {
                            @Override
                            public void onSuccess() {
                                String  userdb = username.replace(".com","");
                                ref1 = new Firebase("https://flickering-inferno-9581.firebaseio.com/"+userdb);
                                ref1.push().setValue(username);
                                Toast.makeText(RegisterFarmerActivity.this, "Successfully created user", Toast.LENGTH_LONG).show();
                                RegisterFarmerActivity.this.finish();
                                startActivity(new Intent(RegisterFarmerActivity.this, LoginActivity.class));
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(RegisterFarmerActivity.this, "Registration Failed, Please Try Again", Toast.LENGTH_LONG).show();
                            }
                        });


                    }

                }
                else if(!validateString(name)){
                    Toast.makeText(this, "Please Enter a valid name", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
                else if(userList.contains(username)){
                    Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
                }else {
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
