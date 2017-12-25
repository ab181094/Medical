package com.csecu.amrit.medical.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.csecu.amrit.medical.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Amrit on 25-12-2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences sharedpreferences = getApplicationContext().
                getSharedPreferences(getString(R.string.mypreference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(getString(R.string.APP_TOKEN), refreshedToken);
        editor.commit();
    }
}