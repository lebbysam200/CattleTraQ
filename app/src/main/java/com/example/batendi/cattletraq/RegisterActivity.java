package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import com.firebase.client.Firebase;
import com.firebase.client.AuthData;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    Button cancel,reg;
    EditText etName, etUsername;
    EditText etPass,etPassConf;

<<<<<<< HEAD
    Firebase ref;

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

<<<<<<< HEAD
        ref = new Firebase(getResources().getString(R.string.firebase_url));

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
        etName = (EditText) findViewById(R.id.name);
        etUsername =(EditText) findViewById(R.id.username);
        etPass = (EditText) findViewById(R.id.password);
        etPassConf = (EditText) findViewById(R.id.pass_conf);

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
<<<<<<< HEAD
                Firebase usersRef = ref.child("users");
                Map<String, String> userMap = new HashMap<String, String>();

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                String conf = etPassConf.getText().toString();

<<<<<<< HEAD
                if(pass.equals(conf)) {

                    userMap.put("name", name);
                    userMap.put("username", username);
                    userMap.put("password", pass);

                    User user = new User(username,name,pass);
                    usersRef.setValue(user);

                    Map<String, Map<String, String>> users = new HashMap<String, Map<String, String>>();
                    users.put("user", userMap);

                    ref.createUser(username, pass, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(RegisterActivity.this, "Successfully created user", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // there was an error
                        }
                    });
                    RegisterActivity.this.finish();
                    startActivity(new Intent(this, LoginActivity.class));

                }else {
                    Toast.makeText(this, "Passwords Do Not Match, Try Again", Toast.LENGTH_LONG).show();
                }


=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
                break;
        }
    }
}
