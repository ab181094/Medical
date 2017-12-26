package com.csecu.amrit.medical.doctorSignup;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.csecu.amrit.medical.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    View view;
    EditText etName, etPassword, etContact, etSex, etSpecial, etQualification;
    FloatingActionButton btSubmit;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        etName = view.findViewById(R.id.personal_etname);
        etPassword = view.findViewById(R.id.personal_etpassword);
        etContact = view.findViewById(R.id.personal_etcontact);
        etSex = view.findViewById(R.id.personal_etsex);
        etSpecial = view.findViewById(R.id.personal_etspecial);
        etQualification = view.findViewById(R.id.personal_etqualification);
        btSubmit = view.findViewById(R.id.personal_btsubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] values = new String[6];
                values[0] = etName.getText().toString().toLowerCase().trim();
                values[1] = etPassword.getText().toString().toLowerCase().trim();
                values[2] = etContact.getText().toString().toLowerCase().trim();
                values[3] = etSex.getText().toString().toLowerCase().trim();
                values[4] = etSpecial.getText().toString().toLowerCase().trim();
                values[5] = etQualification.getText().toString().toLowerCase().trim();

                String[] info = new String[6];
                for (int i = 0; i < values.length; i++) {
                    try {
                        info[i] = URLEncoder.encode(values[i], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        info[i] = values[i];
                    }
                }

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.USER_NAME), info[0]);
                editor.putString(getString(R.string.USER_PASSWORD), info[1]);
                editor.putString(getString(R.string.USER_CONTACT), info[2]);
                editor.putString(getString(R.string.USER_SEX), info[3]);
                editor.putString(getString(R.string.USER_SPECIAL), info[4]);
                editor.putString(getString(R.string.USER_QUALIFICATION), info[5]);
                editor.commit();

                toastIt("Data Saved");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        OtherFragment fragment = new OtherFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.doctor_signup_fragment_container, fragment);
                        fragmentTransaction.commit();
                    }
                }, 2000);
            }
        });
        return view;
    }

    private void toastIt(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
