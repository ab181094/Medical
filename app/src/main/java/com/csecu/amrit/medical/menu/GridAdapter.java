package com.csecu.amrit.medical.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csecu.amrit.medical.R;

/**
 * Created by Amrit on 11-12-2017.
 */

public class GridAdapter extends BaseAdapter {
    private String iconName[];
    private Context context;
    private LayoutInflater inflater;

    public GridAdapter(Context context, String iconName[]) {
        this.context = context;
        this.iconName = iconName;
    }

    @Override
    public int getCount() {
        return iconName.length;
    }

    @Override
    public Object getItem(int position) {
        return iconName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_grid_layout, null);
        }
        TextView menuIcon = gridView.findViewById(R.id.itemName);
        menuIcon.setText(iconName[position]);
        return gridView;
    }
}
