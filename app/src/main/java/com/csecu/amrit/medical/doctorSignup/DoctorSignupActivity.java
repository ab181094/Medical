package com.csecu.amrit.medical.doctorSignup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.csecu.amrit.medical.R;

public class DoctorSignupActivity extends AppCompatActivity {
    String registrationNumber;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            registrationNumber = extras.getString("registration");

            sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.REGISTRATION_NUMBER), registrationNumber);
            editor.commit();
        }

        PersonalFragment fragment = new PersonalFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.doctor_signup_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void toastIt(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
