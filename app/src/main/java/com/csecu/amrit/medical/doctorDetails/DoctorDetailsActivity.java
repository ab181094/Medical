package com.csecu.amrit.medical.doctorDetails;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csecu.amrit.medical.R;
import com.csecu.amrit.medical.doctorList.Doctor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView tvName, tvContact, tvSex, tvSpecial, tvQualification, tvChamber, tvDays,
            tvHours, tvFee, tvReg;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.doctor_details_imageView);
        tvName = (TextView) findViewById(R.id.doctor_details_tvName);
        tvContact = (TextView) findViewById(R.id.doctor_details_tvContact);
        tvSex = (TextView) findViewById(R.id.doctor_details_tvSex);
        tvSpecial = (TextView) findViewById(R.id.doctor_details_tvSpecial);
        tvQualification = (TextView) findViewById(R.id.doctor_details_tvQualification);
        tvChamber = (TextView) findViewById(R.id.doctor_details_tvChamber);
        tvDays = (TextView) findViewById(R.id.doctor_details_tvDays);
        tvHours = (TextView) findViewById(R.id.doctor_details_tvHours);
        tvFee = (TextView) findViewById(R.id.doctor_details_tvFee);
        tvReg = (TextView) findViewById(R.id.doctor_details_tvRegistration);

        final Doctor doctor = getIntent().getParcelableExtra("doctor");
        showInfo(doctor);

        Button fab = (Button) findViewById(R.id.doctor_details_btAppointment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showInfo(Doctor doctor) {
        imageView.setImageBitmap(doctor.getImage());
        tvName.setText(decode(doctor.getName()));
        tvContact.setText(decode(doctor.getContact()));
        tvSex.setText(decode(doctor.getSex()));
        tvSpecial.setText(decode(doctor.getSpecial()));
        tvQualification.setText(decode(doctor.getQualification()));
        tvChamber.setText(decode(doctor.getChamber()));
        tvDays.setText(decode(doctor.getDays()));
        tvHours.setText(decode(doctor.getHours()));
        tvFee.setText(decode(doctor.getFee()));
        tvReg.setText(decode(doctor.getRegistration()));
    }

    public String decode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }

    private void toastIt(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
