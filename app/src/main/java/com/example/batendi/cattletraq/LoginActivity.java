package com.example.batendi.cattletraq;

<<<<<<< HEAD
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
=======
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
//import com.firebase.client.AuthData;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner spinner;
<<<<<<< HEAD
    private Firebase mFirebaseRef;
    Button cancel,login;
    EditText etPass, etUsername;
    private ProgressDialog mAuthProgressDialog;

    private AuthData mAuthData;

    private static final String TAG = LoginActivity.class.getSimpleName();
=======
    Button cancel,login;
    EditText etPass, etUsername;
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

<<<<<<< HEAD
        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));

        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
        etUsername = (EditText) findViewById(R.id.username);
        etPass = (EditText) findViewById(R.id.password);

        // Spinner element
<<<<<<< HEAD
        spinner = (Spinner) findViewById(R.id.login_type);
=======
        Spinner spinner = (Spinner) findViewById(R.id.login_type);
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906

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
<<<<<<< HEAD
        login.setOnClickListener(this);
=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
        cancel.setOnClickListener(this);

    }

<<<<<<< HEAD
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

=======
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906

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
<<<<<<< HEAD
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
//            if (name != null) {
//                mLoggedInStatusTextView.setText("Logged in as " + name + " (" + authData.getProvider() + ")");
//            }
        }
        this.mAuthData = authData;
        /* invalidate options menu to hide/show the logout button */
        supportInvalidateOptionsMenu();
=======

                break;
        }
>>>>>>> 357d3b6f06b0cc34cc4303ed697f9bc2ae437906
    }
}
