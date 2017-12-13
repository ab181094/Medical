package com.csecu.amrit.medical.signup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.csecu.amrit.medical.R;
import com.csecu.amrit.medical.menu.GridAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferenceFragment extends Fragment {
    GridView gridView;
    String iconName[] = {"Doctors", "Diagonostics"};

    public PreferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preference, container, false);
        gridView = view.findViewById(R.id.preferenceGridView);
        GridAdapter adapter = new GridAdapter(getContext(), iconName);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    RegistrationFragment fragment = new RegistrationFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }

    private void toastIt(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
