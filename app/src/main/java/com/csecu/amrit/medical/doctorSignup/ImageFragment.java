package com.csecu.amrit.medical.doctorSignup;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.csecu.amrit.medical.R;
import com.csecu.amrit.medical.backend.BackEnd;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    View view;
    String imageName, name, password, contact, sex, special, qualification;
    String chamber, days, hours, fee, registration, token;
    int RESULT_LOAD_IMAGE = 1;
    ImageView imImage;
    Button btSelect, btUpload;

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);
        imImage = view.findViewById(R.id.image_ivImage);
        btSelect = view.findViewById(R.id.image_btSelect);
        btUpload = view.findViewById(R.id.image_btUpload);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        SharedPreferences sharedpreferences = getActivity().
                getSharedPreferences(getString(R.string.mypreference), Context.MODE_PRIVATE);

        name = sharedPref.getString(getString(R.string.USER_NAME), "");
        password = sharedPref.getString(getString(R.string.USER_PASSWORD), "");
        contact = sharedPref.getString(getString(R.string.USER_CONTACT), "");
        sex = sharedPref.getString(getString(R.string.USER_SEX), "");
        special = sharedPref.getString(getString(R.string.USER_SPECIAL), "");
        qualification = sharedPref.getString(getString(R.string.USER_QUALIFICATION), "");
        chamber = sharedPref.getString(getString(R.string.USER_CHAMBER), "");
        days = sharedPref.getString(getString(R.string.USER_DAYS), "");
        hours = sharedPref.getString(getString(R.string.USER_HOURS), "");
        fee = sharedPref.getString(getString(R.string.USER_FEE), "");
        registration = sharedPref.getString(getString(R.string.REGISTRATION_NUMBER), "");
        token = sharedpreferences.getString(getString(R.string.APP_TOKEN), "");

        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent
                        (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.USER_NAME), imageName);

                Bitmap image = ((BitmapDrawable) imImage.getDrawable()).getBitmap();
                BackEnd backEnd = new BackEnd(getContext());
                backEnd.execute("registration", image, imageName, name, password, contact, sex, special,
                        qualification, chamber, days, hours, fee, registration, token);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imImage.setImageURI(selectedImage);

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            File file = new File(picturePath);
            String filePath = file.getName();

            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String registrationNumber = sharedPref.getString
                    (getString(R.string.REGISTRATION_NUMBER), null);
            String contact = sharedPref.getString
                    (getString(R.string.USER_CONTACT), null);
            imageName = registrationNumber + "_" + contact;
            imageName = imageName + filePath.substring(filePath.lastIndexOf("."));
            File renamedFile = new File(imageName);
            file.renameTo(renamedFile);
            toastIt(file.getName());
        }
    }

    private void toastIt(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
