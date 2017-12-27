package com.csecu.amrit.medical.backend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.csecu.amrit.medical.doctorSignup.DoctorSignupActivity;
import com.csecu.amrit.medical.menu.MenuActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Amrit on 12-12-2017.
 */

public class BackEnd extends AsyncTask {
    public AsyncResponse delegate = null;
    String input_url = "http://192.168.137.1/medical/";
    // String input_url = "http://10.2.3.100/medical/";
    String malformed_url_error = "Malformed URL has occurred";
    String unsupported_encoding_error = "Character Encoding is not supported";
    String protocol_exception_error = "There is an error in the underlying protocol";
    String io_exception_error = "I/O exception of some sort has occurred";
    String unknown_error = "Unknown error has occurred";
    String reg_success_msg = "Registration number found";
    String reg_failed_msg = "Registration number not found";
    String registrationNumber;
    String type;
    Context context;
    String JSON_STRING = null;

    public BackEnd(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] params) {
        type = (String) params[0];
        if (type == "check") {
            String number = (String) params[1];
            registrationNumber = number;
            input_url = input_url + "check.php";
            String data = null;
            try {
                data = URLEncoder.encode("number", "UTF-8") + "=" +
                        URLEncoder.encode(number, "UTF-8");
                return sendData(input_url, data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return unsupported_encoding_error;
            }
        } else if (type == "get") {
            String get_url = input_url + "select.php";
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
        } else if (type == "registration") {
            Bitmap image = (Bitmap) params[1];
            String imageName = (String) params[2];
            String name = (String) params[3];
            String password = (String) params[4];
            String contact = (String) params[5];
            String sex = (String) params[6];
            String special = (String) params[7];
            String qualification = (String) params[8];
            String chamber = (String) params[9];
            String days = (String) params[10];
            String hours = (String) params[11];
            String fee = (String) params[12];
            String registration = (String) params[13];
            String token = (String) params[14];

            String url = input_url + "insert.php";

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            String encodedImage = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);

            String data = null;
            try {
                data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("contact", "UTF-8") + "=" +
                        URLEncoder.encode(contact, "UTF-8") + "&" +
                        URLEncoder.encode("sex", "UTF-8") + "=" +
                        URLEncoder.encode(sex, "UTF-8") + "&" +
                        URLEncoder.encode("special", "UTF-8") + "=" +
                        URLEncoder.encode(special, "UTF-8") + "&" +
                        URLEncoder.encode("qualification", "UTF-8") + "=" +
                        URLEncoder.encode(qualification, "UTF-8") + "&" +
                        URLEncoder.encode("chamber", "UTF-8") + "=" +
                        URLEncoder.encode(chamber, "UTF-8") + "&" +
                        URLEncoder.encode("days", "UTF-8") + "=" +
                        URLEncoder.encode(days, "UTF-8") + "&" +
                        URLEncoder.encode("hours", "UTF-8") + "=" +
                        URLEncoder.encode(hours, "UTF-8") + "&" +
                        URLEncoder.encode("fee", "UTF-8") + "=" +
                        URLEncoder.encode(fee, "UTF-8") + "&" +
                        URLEncoder.encode("registration", "UTF-8") + "=" +
                        URLEncoder.encode(registration, "UTF-8") + "&" +
                        URLEncoder.encode("imageName", "UTF-8") + "=" +
                        URLEncoder.encode(imageName, "UTF-8") + "&" +
                        URLEncoder.encode("token", "UTF-8") + "=" +
                        URLEncoder.encode(token, "UTF-8");

                String result = sendData(url, data);

                url = input_url + "upload.php";

                data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(imageName, "UTF-8")
                        + "&" + URLEncoder.encode("image", "UTF-8") + "=" +
                        URLEncoder.encode(encodedImage, "UTF-8");
                result = result + " and " + sendData(url, data);
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return unsupported_encoding_error;
            }
        } else if (type == "appointment") {
            String name = (String) params[1];
            String contact = (String) params[2];
            String sex = (String) params[3];
            String age = (String) params[4];
            String token = (String) params[5];
            String regNo = (String) params[6];
            String doctorToken = (String) params[7];

            String url = input_url + "appointment.php";

            String data = null;
            try {
                data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("contact", "UTF-8") + "=" +
                        URLEncoder.encode(contact, "UTF-8") + "&" +
                        URLEncoder.encode("sex", "UTF-8") + "=" +
                        URLEncoder.encode(sex, "UTF-8") + "&" +
                        URLEncoder.encode("age", "UTF-8") + "=" +
                        URLEncoder.encode(age, "UTF-8") + "&" +
                        URLEncoder.encode("token", "UTF-8") + "=" +
                        URLEncoder.encode(token, "UTF-8") + "&" +
                        URLEncoder.encode("registration", "UTF-8") + "=" +
                        URLEncoder.encode(regNo, "UTF-8");

                String result = sendData(url, data);

                url = input_url + "send.php";
                data = URLEncoder.encode("token", "UTF-8") + "=" +
                        URLEncoder.encode(doctorToken, "UTF-8");
                sendData(url, data);
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return unsupported_encoding_error;
            }
        } else {
            return unknown_error;
        }
    }

    private String sendData(String input_url, String data) {
        try {
            URL url = new URL(input_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter
                    (new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return malformed_url_error;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return unsupported_encoding_error;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return protocol_exception_error;
        } catch (IOException e) {
            e.printStackTrace();
            return io_exception_error;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        result = result.toString().trim();

        if (type == "check") {
            if (result.equals("1")) {
                toastIt(reg_success_msg);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, DoctorSignupActivity.class);
                        intent.putExtra("registration", registrationNumber);
                        context.startActivity(intent);
                    }
                }, 2000);
            } else {
                toastIt(reg_failed_msg);
            }
        } else if (type == "registration") {
            toastIt(result.toString());
            Log.d("MSG", "Message: " + result.toString());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, MenuActivity.class);
                    context.startActivity(intent);
                }
            }, 2000);
        } else if (type == "get") {
            delegate.processFinish(result);
        } else if (type == "appointment") {
            delegate.processFinish(result);
        }
    }

    private void toastIt(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
