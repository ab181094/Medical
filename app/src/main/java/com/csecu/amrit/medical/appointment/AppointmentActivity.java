package com.csecu.amrit.medical.appointment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csecu.amrit.medical.R;
import com.csecu.amrit.medical.backend.AsyncResponse;
import com.csecu.amrit.medical.backend.BackEnd;
import com.csecu.amrit.medical.doctorList.Doctor;
import com.csecu.amrit.medical.menu.MenuActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AppointmentActivity extends AppCompatActivity implements AsyncResponse {
    EditText etName, etContact, etSex, etAge;
    String token, regNo;
    BackEnd backEnd;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = (EditText) findViewById(R.id.appointment_etName);
        etContact = (EditText) findViewById(R.id.appointment_etContact);
        etSex = (EditText) findViewById(R.id.appointment_etSex);
        etAge = (EditText) findViewById(R.id.appointment_etAge);

        backEnd = new BackEnd(getApplicationContext());
        backEnd.delegate = this;

        doctor = getIntent().getParcelableExtra("doctor");
        regNo = doctor.getRegistration();

        SharedPreferences sharedpreferences = getApplicationContext().
                getSharedPreferences(getString(R.string.mypreference), Context.MODE_PRIVATE);
        token = sharedpreferences.getString(getString(R.string.APP_TOKEN), "");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] values = new String[4];
                values[0] = etName.getText().toString().toLowerCase().trim();
                values[1] = etContact.getText().toString().toLowerCase().trim();
                values[2] = etSex.getText().toString().toLowerCase().trim();
                values[3] = etAge.getText().toString().toLowerCase().trim();

                String[] info = new String[6];
                for (int i = 0; i < values.length; i++) {
                    try {
                        info[i] = URLEncoder.encode(values[i], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        info[i] = values[i];
                    }
                }

                backEnd.execute("appointment", info[0], info[1], info[2], info[3], token, regNo);
            }
        });
    }

    private void toastIt(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processFinish(Object output) {
        toastIt(output.toString());
        etName.setText("");
        etContact.setText("");
        etSex.setText("");
        etAge.setText("");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AppointmentActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}
