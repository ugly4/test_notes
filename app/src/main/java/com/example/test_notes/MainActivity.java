package com.example.test_notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    //static ArrayList<String> notes = new ArrayList<>();
    static ArrayList<NoteClass> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static ListAdapter myAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_note) {

            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        //получаем сохранённые значения
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test_notes", Context.MODE_PRIVATE);
        HashSet<String> setNotes = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        HashSet<String> setDates = (HashSet<String>) sharedPreferences.getStringSet("dates", null);


        if (setNotes == null || setDates == null) {
            //создаём начанальную заметку при первом запуске приложения
            notes.add(new NoteClass("Начальная заметка", String.valueOf(Calendar.getInstance().getTime())));
        } else {
            //инициализируем сохранённые заметки
            ArrayList<String> listNotes = new ArrayList<>(setNotes);
            ArrayList<String> listDates = new ArrayList<>(setDates);
            notes = new ArrayList<>();
            for (int i = 0; i < setNotes.size(); i++){
                notes.add(new NoteClass(listNotes.get(i), listDates.get(i)));
            }
        }

        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);
        myAdapter = new ListAdapter(this, notes);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //открываем окно редактирования заметки
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });

        //при долгом нажатии на заметку можем её удалить
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //обновляем и сохраняем список заметок
                                notes.remove(itemToDelete);
                                myAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test_notes", Context.MODE_PRIVATE);
                                ArrayList<String> listNotes = new ArrayList<>();
                                ArrayList<String> listDates = new ArrayList<>();
                                for (int j = 0; i < MainActivity.notes.size(); i++){
                                    listNotes.add(MainActivity.notes.get(j).getData());
                                    listDates.add(MainActivity.notes.get(j).getDate());
                                }
                                HashSet<String> setNotes = new HashSet(listNotes);
                                HashSet<String> setDates = new HashSet(listDates);
                                sharedPreferences.edit().putStringSet("notes", setNotes).apply();
                                sharedPreferences.edit().putStringSet("dates", setDates).apply();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });
    }
}
