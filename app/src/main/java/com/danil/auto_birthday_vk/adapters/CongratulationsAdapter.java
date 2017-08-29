package com.danil.auto_birthday_vk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.danil.auto_birthday_vk.Congratulations;
import com.danil.auto_birthday_vk.R;

import java.util.ArrayList;

public class CongratulationsAdapter extends BaseAdapter {

    private ArrayList<Congratulations> m_arrData = null;

    public CongratulationsAdapter(ArrayList<Congratulations> arrData){
        m_arrData = arrData;
    }



    @Override
    public int getCount() {
        return m_arrData.size();
    }

    @Override
    public Object getItem(int position) {
        return m_arrData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Congratulations)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater li = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.congratulations_item_record, parent, false);
        }
        Congratulations info = ((Congratulations)getItem(position));
        TextView tv = (TextView)convertView.findViewById(R.id.congratulationsTextViewItem);
        tv.setText(info.toString());
        return convertView;
    }
}
