package com.csecu.amrit.medical.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.csecu.amrit.medical.R;
import com.csecu.amrit.medical.doctorList.ListActivity;
import com.csecu.amrit.medical.signup.SignupActivity;

public class MenuActivity extends AppCompatActivity {
    GridView gridView;
    String iconName[] = {"Doctors", "Diagonostics", "Map", "Sign In", "Sign Up"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.menuGridView);
        GridAdapter adapter = new GridAdapter(this, iconName);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    // toastIt("Clicked Icon: " + iconName[position]);
                    Intent intent = new Intent(MenuActivity.this, SignupActivity.class);
                    startActivity(intent);
                } else if (position == 0) {
                    // toastIt("Clicked Icon: " + iconName[position]);
                    Intent intent = new Intent(MenuActivity.this, ListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void toastIt(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
