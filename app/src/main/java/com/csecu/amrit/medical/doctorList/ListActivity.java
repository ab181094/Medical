package com.csecu.amrit.medical.doctorList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.csecu.amrit.medical.R;
import com.csecu.amrit.medical.backend.AsyncResponse;
import com.csecu.amrit.medical.backend.BackEnd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AsyncResponse {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.doctorListView);
        BackEnd backEnd = new BackEnd(this);
        backEnd.delegate = this;
        backEnd.execute("get");
    }

    private ArrayList<Doctor> parseAll(String result) {
        JSONObject jsonObject;
        JSONArray jsonArray;

        ArrayList<Doctor> doctorList = new ArrayList<>();
        try {
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("server_response");
            for (int count = 0; count < jsonArray.length(); count++) {
                JSONObject object = jsonArray.getJSONObject(count);
                int idInt = object.getInt("id");
                String id = String.valueOf(idInt);
                String name = object.getString("name");
                String password = object.getString("password");
                String contact = object.getString("contact");
                String sex = object.getString("sex");
                String special = object.getString("special");
                String qualification = object.getString("qualification");
                String chamber = object.getString("chamber");
                String days = object.getString("days");
                String hours = object.getString("hours");
                String fee = object.getString("fee");
                String registration = object.getString("registration");
                String imageName = object.getString("image");
                String token = object.getString("token");

                Doctor doctor = new Doctor(id, name, password, contact, sex, special,
                        qualification, chamber, days, hours, fee, registration, imageName,
                        token);
                doctorList.add(doctor);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return doctorList;
    }

    private void showList(ArrayList<Doctor> doctorsList) {
        if (doctorsList != null && doctorsList.size() > 0) {
            listView.setAdapter(new MyCustomAdapter(getApplicationContext(), doctorsList));
        } else if (doctorsList.size() == 0) {
            toastIt("No data found");
        }
    }

    private void toastIt(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processFinish(Object output) {
        String result = output.toString().trim();
        ArrayList<Doctor> doctorList = parseAll(result);
        Information information = new Information();
        information.execute(doctorList);
        // showList(doctorList);
    }

    class Information extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            ArrayList<Doctor> newDoctorList = new ArrayList<>();
            ArrayList<Doctor> doctorList = (ArrayList<Doctor>) params[0];
            for (int i = 0; i < doctorList.size(); i++) {
                Doctor doctor = doctorList.get(i);
                String imageName = doctor.getImageName();
                Log.d("Name", imageName);

                // String get_url = "http://192.168.137.1/medical/" + imageName;
                String get_url = "http://10.2.3.100/medical/" + imageName;
                try {
                    URL url = new URL(get_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    Bitmap image = BitmapFactory.decodeStream(inputStream, null, null);
                    doctor.setImage(image);
                    newDoctorList.add(doctor);
                    Log.d("Size", String.valueOf(newDoctorList.size()));

                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return newDoctorList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList<Doctor> doctorList = (ArrayList<Doctor>) o;
            showList(doctorList);
        }
    }
}
