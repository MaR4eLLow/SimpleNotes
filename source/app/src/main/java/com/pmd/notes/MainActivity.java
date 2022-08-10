package com.pmd.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplication().setTheme(R.style.AppTheme);
        setTheme(R.style.AppTheme);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Note> NotesAL = new ArrayList<Note>();
        Map<Integer, Map<String, String>> NotesData = new Database(this).GetAllNotes();
        for (Integer id : NotesData.keySet()){
            NotesAL.add(new Note(NotesData.get(id).get("theme"),NotesData.get(id).get("text"),NotesData.get(id).get("color"),id));
        }
        if (NotesAL.size() > 0){
            findViewById(R.id.UserNotify).setVisibility(View.GONE);
            findViewById(R.id.NotesList).setVisibility(View.VISIBLE);
        }

        GridView GV = findViewById(R.id.NotesList);
        NoteAdapter na = new NoteAdapter(this, R.layout.notes_list, NotesAL);


        GV.setAdapter(na);
        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
                intent.putExtra("isEdit",true);
                intent.putExtra("Color",NotesAL.get(i).getColor());
                intent.putExtra("Theme",NotesAL.get(i).getTheme());
                intent.putExtra("Text",NotesAL.get(i).getText());
                intent.putExtra("Id",NotesAL.get(i).getId());
                startActivity(intent);

            }
        });

        findViewById(R.id.NewNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditNoteActivity.class);
                intent.putExtra("isEdit",false);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        onStart();
    }
}