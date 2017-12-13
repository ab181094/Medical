package com.csecu.amrit.medical.backend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class BackEnd extends AsyncTask<String, Void, String> {
    String input_url = "";
    String malformed_url_error = "Malformed URL has occurred";
    String unsupported_encoding_error = "Character Encoding is not supported";
    String protocol_exception_error = "There is an error in the underlying protocol";
    String io_exception_error = "I/O exception of some sort has occurred";
    String unknown_error = "Unknown error has occurred";
    Context context;

    public BackEnd(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if (type == "check") {
            String number = params[1];
            input_url = "http://10.2.3.100/medical/check.php";

            try {
                URL url = new URL(input_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter
                        (new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("number", "UTF-8") + "=" +
                        URLEncoder.encode(number, "UTF-8");
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
        } else {
            return unknown_error;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        toastIt(s);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, ViewListActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void toastIt(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
