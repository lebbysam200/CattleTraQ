package com.example.batendi.cattletraq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

<<<<<<< HEAD
import com.firebase.client.Firebase;

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    Button login,register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        Firebase.setAndroidContext(this);
=======

>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }

    }
}
