package com.csecu.amrit.medical.signup;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.csecu.amrit.medical.R;
import com.csecu.amrit.medical.backend.BackEnd;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {
    View view;
    EditText etNumber;
    FloatingActionButton fab;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        etNumber = view.findViewById(R.id.registration_etRegNo);
        fab = view.findViewById(R.id.registration_btCheck);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "check";
                String number = etNumber.getText().toString().toLowerCase().trim();
                // toastIt(number);
                BackEnd backEnd = new BackEnd(getContext());
                backEnd.execute(type, number);
            }
        });
        return view;
    }

    private void toastIt(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
