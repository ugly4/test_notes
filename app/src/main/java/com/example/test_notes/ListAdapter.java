package com.example.test_notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private LayoutInflater LInflater;
    private ArrayList<NoteClass> list;

    public ListAdapter(Context context, ArrayList<NoteClass> data){

        list = data;
        LInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        View v = view;

        if ( v == null){
            holder = new ViewHolder();
            v = LInflater.inflate(R.layout.list_item, viewGroup, false);
            holder.text = (TextView) v.findViewById(R.id.note_text);
            holder.date = (TextView) v.findViewById(R.id.note_date);
            v.setTag(holder);
        }

        holder = (ViewHolder) v.getTag();
        NoteClass data = getData(i);

        holder.text.setText(data.getData());
        holder.date.setText(data.getDate());

        return v;
    }

    NoteClass getData(int position){
        return (NoteClass) getItem(position);
    }

    private static class ViewHolder {
        private TextView text;
        private TextView date;
    }
}
