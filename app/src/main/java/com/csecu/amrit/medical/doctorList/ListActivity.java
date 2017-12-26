package com.csecu.amrit.medical.doctorList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.csecu.amrit.medical.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.doctorListView);
        Information information = new Information();
        information.execute();
    }

    private void toastIt(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    class Information extends AsyncTask {
        String JSON_STRING = null;
        // String get_url = "http://192.168.137.1/medical/select.php";
        String get_url = "http://10.2.3.100/medical/select.php";
        String malformed_url_error = "Malformed URL has occurred";
        String unsupported_encoding_error = "Character Encoding is not supported";
        String protocol_exception_error = "There is an error in the underlying protocol";
        String io_exception_error = "I/O exception of some sort has occurred";
        String unknown_error = "Unknown error has occurred";
        String reg_success_msg = "Registration number found";
        String reg_failed_msg = "Registration number not found";

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                URL url = new URL(get_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return malformed_url_error;
            } catch (IOException e) {
                e.printStackTrace();
                return io_exception_error;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            String result = o.toString().trim();
            ArrayList<Doctor> doctorsList = parseAll(result);
            showList(doctorsList);
        }

        private void showList(ArrayList<Doctor> doctorsList) {
            if (doctorsList != null && doctorsList.size() > 0) {
                listView.setAdapter(new MyCustomAdapter(getApplicationContext(), doctorsList));
            } else if (doctorsList.size() == 0) {
                toastIt("No data found");
            }
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
    }
}
