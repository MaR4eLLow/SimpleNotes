package com.pmd.notes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database
{
    private SQLiteDatabase db;
    private Context ctext;
    private void Save(Cursor cur){
        if (db != null) db.close();
        if (cur != null) cur.close();
        db = ctext.openOrCreateDatabase("notes.db",Context.MODE_PRIVATE, null);
    }

    public Database(Context context){
        ctext = context;
        try {
            db = context.openOrCreateDatabase("notes.db",Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, theme TEXT,text TEXT, color TEXT)");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        Save(null);
    }

    public Map<Integer, Map<String,String>> GetAllNotes(){
        db.execSQL("DELETE FROM notes WHERE color = 'null' OR color = NULL");
        Cursor query = db.rawQuery("SELECT * FROM notes",null);
        if(query.moveToFirst()){
            Map<Integer, Map<String,String>> res = new HashMap<>();
            while(query.moveToNext()){
                Map<String,String> data = new HashMap<>();
                data.put("theme", query.getString(1));
                data.put("text", query.getString(2));
                data.put("color", query.getString(3));
                res.put(query.getInt(0),data);
            }
            Save(query);
            return res;
        }else{
            Save(query);
            return new HashMap<>();
        }

    }
    public Map<String,String> GetNote(Integer id){
        Cursor query = db.rawQuery("SELECT * FROM notes WHERE id = '" +
                id.toString() +
                "'",null);
        if(query.moveToFirst()){
                Map<String,String> data = new HashMap<>();
                data.put("theme", query.getString(1));
                data.put("text", query.getString(2));
                data.put("color", query.getString(3));
            Save(query);
            return data;
        }else{
            Save(query);
            return new HashMap<>();
        }

    }

    public Integer NewNote(String Theme,String Text, String Color){
            Integer res;
            db.execSQL(String.format("INSERT INTO notes (theme,text,color) VALUES ('%s','%s','%s')",Theme,Text,Color));
            Cursor query = db.rawQuery("SELECT MAX(id) FROM notes",null);
            if (query.moveToFirst()){
                res = query.getInt(0);
            }else{
                res = 0;
            }
            Save(query);
            return res;
    }
    public void UpdateNote(Integer id,String Theme, String Text, String Color){
        db.execSQL(String.format("UPDATE notes SET theme = '%s', text = '%s', color = '%s' WHERE id = %d",Theme,Text,Color,id));
        Save(null);
    }
    public void DeleteNote(Integer id){
        db.execSQL(String.format("DELETE FROM notes WHERE id = %d",id));
        Save(null);
    }



}
