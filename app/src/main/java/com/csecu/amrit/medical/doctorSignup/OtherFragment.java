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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csecu.amrit.medical.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {
    EditText etChamber, etDays, etHours, etFee;
    Button btAdd;
    FloatingActionButton btSubmit;
    ArrayList<String> hoursList = new ArrayList<>();

    public OtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        etChamber = view.findViewById(R.id.other_etChamber);
        etDays = view.findViewById(R.id.other_etDays);
        etHours = view.findViewById(R.id.other_etHours);
        etFee = view.findViewById(R.id.other_etfee);
        btAdd = view.findViewById(R.id.other_btadd);
        btSubmit = view.findViewById(R.id.other_btSubmit);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = etHours.getText().toString().toLowerCase().trim();
                if (!hoursList.contains(hour)) {
                    hoursList.add(hour);
                } else {
                    toastIt("It has been added already");
                }
                etHours.setText("");
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] values = new String[4];
                values[0] = etChamber.getText().toString().toLowerCase().trim();
                values[1] = etDays.getText().toString().toLowerCase().trim();
                values[2] = etHours.getText().toString().toLowerCase().trim();
                values[3] = etFee.getText().toString().toLowerCase().trim();

                String hour = "";
                if (hoursList.size() != 0) {
                    for (int i = 0; i < hoursList.size(); i++) {
                        String data = hoursList.get(i);
                        hour = hour + data;
                        hour = hour + ", ";
                    }
                }

                values[2] = hour + values[2];

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
                editor.putString(getString(R.string.USER_CHAMBER), info[0]);
                editor.putString(getString(R.string.USER_DAYS), info[1]);
                editor.putString(getString(R.string.USER_HOURS), info[2]);
                editor.putString(getString(R.string.USER_FEE), info[3]);
                editor.commit();

                toastIt("Data Saved");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageFragment fragment = new ImageFragment();
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
