package com.example.batendi.cattletraq;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinner;
    private Firebase mFirebaseRef;
    private ProgressDialog mAuthProgressDialog;

    private AuthData mAuthData;

    private static final String TAG = LoginActivity.class.getSimpleName();
    Button cancel,login;
    EditText etPass, etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));

        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);

        etUsername = (EditText) findViewById(R.id.username);
        etPass = (EditText) findViewById(R.id.password);

        // Spinner element
        spinner = (Spinner) findViewById(R.id.login_type);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Farmer");
        categories.add("Herdboy/Herdman");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        login = (Button) findViewById(R.id.login_confirm);
        cancel = (Button) findViewById(R.id.cancel);

        login.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //TextView myText = (TextView) view;
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(this, "Login as " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                onBackPressed();
                break;
            case R.id.login_confirm:
                mAuthProgressDialog.show();
                mFirebaseRef.authWithPassword(etUsername.getText().toString(), etPass.getText().toString(), new AuthResultHandler("password"));
                break;
        }
    }

    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            mAuthProgressDialog.hide();
            Log.i(TAG, provider + " auth successful");
            setAuthenticatedUser(authData);
            LoginActivity.this.finish();
            startActivity(new Intent(LoginActivity.this,FarmerHomeActivity.class));
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mAuthProgressDialog.hide();
            Toast.makeText(LoginActivity.this, "Wrong Email or Password, Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            String name = null;
            if (authData.getProvider().equals("password")) {
                name = authData.getUid();
            } else {
                Log.e(TAG, "Invalid provider: " + authData.getProvider());
            }
        }
        this.mAuthData = authData;
        supportInvalidateOptionsMenu();
        }
}

