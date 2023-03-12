package com.example.test_notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);


        Intent intent = getIntent();

        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId).getData());
        } else {

            MainActivity.notes.add(new NoteClass("",String.valueOf(Calendar.getInstance().getTime())));
            noteId = MainActivity.notes.size() - 1;
            MainActivity.myAdapter.notifyDataSetChanged();

        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notes.set(noteId, new NoteClass (String.valueOf(charSequence), String.valueOf(Calendar.getInstance().getTime() )));
                MainActivity.myAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test_notes", Context.MODE_PRIVATE);
                ArrayList<String> listNotes = new ArrayList<>();
                ArrayList<String> listDates = new ArrayList<>();
                for (int j = 0; j < MainActivity.notes.size(); j++){
                    listNotes.add(MainActivity.notes.get(j).getData());
                    listDates.add(MainActivity.notes.get(j).getDate());
                }
                HashSet<String> setNotes = new HashSet(listNotes);
                HashSet<String> setDates = new HashSet(listDates);
                sharedPreferences.edit().putStringSet("notes", setNotes).apply();
                sharedPreferences.edit().putStringSet("dates", setDates).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
