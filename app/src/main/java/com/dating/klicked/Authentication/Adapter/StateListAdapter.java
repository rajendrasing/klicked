package com.dating.klicked.Authentication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dating.klicked.Model.CountryModel;
import com.dating.klicked.R;

import java.util.ArrayList;

public class StateListAdapter extends BaseAdapter {
    Context context;
    ArrayList<CountryModel> arrayListt;
    LayoutInflater inflter;

    public StateListAdapter(Context context, ArrayList<CountryModel> arrayList) {
        this.context = context;
        this.arrayListt = arrayList;
        inflter = (LayoutInflater.from(context));

    }




    @Override
    public int getCount() {
        return arrayListt.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.country_layout, null);
        TextView coinNames = (TextView) view.findViewById(R.id.name);

        if (arrayListt.get(position).getId().equalsIgnoreCase("00"))
        {
            coinNames.setText(arrayListt.get(position).getName());

        }
        else {
            coinNames.setText(arrayListt.get(position).getName());
            coinNames.setTextColor(context.getResources().getColor(R.color.text_tittle));
        }




        return view;
    }



}
