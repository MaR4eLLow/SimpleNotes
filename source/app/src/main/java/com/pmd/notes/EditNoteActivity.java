package com.pmd.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {
    Integer Id;
    EditText Theme, Text;
    Boolean isDeleted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Theme = findViewById(R.id.Theme);
        Text = findViewById(R.id.Text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!(boolean) getIntent().getExtras().get("isEdit")) {
            Id = new Database(this).NewNote(Theme.getText().toString(), Text.getText().toString(), Note.getConstColor("yellow"));
            findViewById(R.id.lbg).setBackgroundColor(Color.parseColor(Note.getConstColor("yellow")));
        }else{
            findViewById(R.id.lbg).setBackgroundColor(Color.parseColor((String) getIntent().getExtras().get("Color")));
            Id = (Integer) getIntent().getExtras().get("Id");
            Theme.setText((String) getIntent().getExtras().get("Theme"));
            Text.setText((String) getIntent().getExtras().get("Text"));
        }

        findViewById(R.id.SelectColorBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectColorDialog().show(getSupportFragmentManager(),"SelectColor");
            }
        });

        findViewById(R.id.DeleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditNoteActivity.this).setTitle("Вы уверены?")
                        .setMessage("После этого действия заметку невозможно будет восстановить")
                        .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Database(EditNoteActivity.this).DeleteNote(Id);
                                isDeleted = true;
                                finish();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

            }
        });
    }


    protected void SaveNote(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.lbg);
        ColorDrawable viewColor = (ColorDrawable) layout.getBackground();
        int colorId = viewColor.getColor();
        String hexColor = String.format("#%06X", (0xFFFFFF & colorId));

        new Database(this).UpdateNote(Id,Theme.getText().toString(),Text.getText().toString(),hexColor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SaveNote();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SaveNote();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isDeleted) {
            SaveNote();
            Toast.makeText(this, "Заметка сохранена!", Toast.LENGTH_SHORT).show();
        }
    }
}