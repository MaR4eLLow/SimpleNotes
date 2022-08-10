package com.pmd.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context mContext;
    private LayoutInflater inflater;
    private int layout;
    private List<Note> notes;
    public NoteAdapter(Context context,int resource, List<Note> Notes){
        super(context,resource,Notes);
        this.notes = Notes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        try{
            @SuppressLint("ViewHolder") View view = inflater.inflate(this.layout,parent,false);

            TextView themeView = view.findViewById(R.id.theme);
            TextView someTextView = view.findViewById(R.id.sometext);
            LinearLayout backgroundView = view.findViewById(R.id.background);

            Note note = notes.get(position);

            themeView.setText(note.getTheme());
            someTextView.setText(note.getText());
            backgroundView.setBackgroundColor(Color.parseColor(note.getColor()));
            return view;
        }catch (Exception ex){
            System.out.println(ex);
            return new View(null);
        }

    }

}
