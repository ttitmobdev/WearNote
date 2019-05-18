package com.example.wearnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Note> {

    public ListViewAdapter( Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
       if (position == 0) {
           return LayoutInflater.from(parent.getContext()).inflate(R.layout.new_note, parent, false);
       }
        convertView = LayoutInflater.from(this.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        Note note = getItem(position);
        TextView title = convertView.findViewById(android.R.id.text1);
        title.setText(note.getTitle().toString());
        return convertView;
    }
}
