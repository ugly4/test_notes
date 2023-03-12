package com.example.test_notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


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

    //Метод, в котором формируется внешний вид элементов с его наполнением
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        View v = view;

        //Если вид элемента не создан, производится его создание
        //с помощью ViewHolder и тегирование данного элемента конкретным holder объектом
        if ( v == null){
            holder = new ViewHolder();
            v = LInflater.inflate(R.layout.list_item, viewGroup, false);
            holder.text = (TextView) v.findViewById(R.id.note_text);
            holder.date = (TextView) v.findViewById(R.id.note_date);
            v.setTag(holder);
        }

        /*
         * После того, как все элементы определены, производится соотнесение
         * внешнего вида, данных и конкретной позиции в ListView.
         * После чего из ArrayList забираются данные для элемента ListView и
         * передаются во внешний вид элемента
         */
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
