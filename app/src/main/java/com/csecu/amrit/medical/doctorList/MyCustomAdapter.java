package com.csecu.amrit.medical.doctorList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csecu.amrit.medical.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by Amrit on 26-12-2017.
 */

public class MyCustomAdapter extends ArrayAdapter<Doctor> {
    public MyCustomAdapter(Context context, List<Doctor> objects) {
        super(context, R.layout.doctors_list, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        ViewHolder viewHolder;

        if (convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.doctors_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = row.findViewById(R.id.doctorImageView);
            viewHolder.tvName = row.findViewById(R.id.doctor_tvName);
            viewHolder.tvSpecial = row.findViewById(R.id.doctor_tvSpecial);
            viewHolder.tvChamber = row.findViewById(R.id.doctor_tvChamber);

            row.setTag(viewHolder);
        } else {
            row = convertView;
            viewHolder = (ViewHolder) row.getTag();
        }

        Doctor item = getItem(position);

        String name = item.getName();
        String special = item.getSpecial();
        String chamber = item.getChamber();

        try {
            name = URLDecoder.decode(name, "UTF-8");
            special = URLDecoder.decode(special, "UTF-8");
            chamber = URLDecoder.decode(chamber, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        viewHolder.imageView.setImageBitmap(item.getImage());
        viewHolder.tvName.setText(name);
        viewHolder.tvSpecial.setText(special);
        viewHolder.tvChamber.setText(chamber);

        return row;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvSpecial;
        TextView tvChamber;
    }
}
