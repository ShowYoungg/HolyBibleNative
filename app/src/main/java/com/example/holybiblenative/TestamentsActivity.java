package com.example.holybiblenative;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NavUtils;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TestamentsActivity extends AppCompatActivity{

    private AppDatabase mDb;
    private ArrayList<DataObject> dl;
    private int testament;
    private String[] books;
    private Integer[] chapters;
    private boolean twoPane = false;
    private String[] all2;

    private String[] all = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy",
            "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings",
            "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms",
            "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
            "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum",
            "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark",
            "Luke", "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians",
            "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians",
            "1 Timothy", "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter",
            "1 John", "2 John", "3 John", "Jude", "Revelation"};

    private Integer[] allChapters = {50,40,27,36,34,24,21,4,31,24,22,25,29,36,10,13,10,42,150,31,
            12,8,66,52,5,48,12,14,3,9,1,4,7,3,3,3,2,14,4,28,16,24,21,28,16,16,13,6,6,4,4,
            5,3,6,4,3,1,13,5,5,3,5,1,1,1,22};

    private Integer[] oldChapters = {50,40,27,36,34,24,21,4,31,24,22,25,29,36,10,13,10,42,150,31,
            12,8,66,52,5,48,12,14,3,9,1,4,7,3,3,3,2,14,4};

    private Integer[] newChapters = {28,16,24,21,28,16,16,13,6,6,4,4,5,3,6,4,3,1,13,5,5,3,5,1,1,1,22};

    private String[] oldTestament = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy",
            "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings",
            "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms",
            "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
            "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum",
            "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi"};

    private String[] newTestament = {"Matthew", "Mark", "Luke", "John", "Acts", "Romans",
            "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians",
            "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy",
            "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter",
            "1 John", "2 John", "3 John", "Jude", "Revelation"};


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testaments);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dl = new ArrayList<>();
        mDb = AppDatabase.getInstance(this);
        all2 = all;

        if (findViewById(R.id.books_list2_600) != null){
            twoPane = true;
        }

        if (getIntent() != null){
            testament = getIntent().getIntExtra("Position", 0);
            if (testament == 0){
                books = all;
                chapters = allChapters;
            } else if (testament == 1){
                books = oldTestament;
                chapters = oldChapters;
            } else {
                books = newTestament;
                chapters = newChapters;
            }
        }
        displayBooks();
    }

    private void displayBooks() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.books_list_view, books );
        if (twoPane){
            GridView gridView = findViewById(R.id.books_list2_600);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(TestamentsActivity.this,
                            DisplayActivity.class).putExtra("DATABASE_TO_USE", confirmDb(books[position]))
                            .putExtra("Book", books[position])
                            .putExtra("Number of Chapters", chapters[position]);
                    startActivity(intent);
                }
            });
        } else {
            ListView listView = findViewById(R.id.books_list2);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(TestamentsActivity.this,
                            ChapterDisplayActivity.class).putExtra("DATABASE_TO_USE", confirmDb(books[position]))
                            .putExtra("Book", books[position])
                            .putExtra("Number of Chapters", chapters[position]);
                    startActivity(intent);
                    Toast.makeText(TestamentsActivity.this, books[position], Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String database_toUse;
    private String confirmDb(String book){
        String s = book;
        if (s.equals("Galatians") || s.equals("Mark") || s.equals("Ephesians") || s.equals("3 John")
            || s.equals("Philippians") || s.equals("Colossians") || s.equals("1 Thessalonians")
            || s.equals("2 Thessalonians")|| s.equals("Jude") || s.equals("1 Timothy")
            || s.equals("2 Timothy") || s.equals("Titus") || s.equals("Philemon")
            || s.equals("Hebrews") || s.equals("James") || s.equals("1 Peter")
            || s.equals("2 Peter") || s.equals("1 John") || s.equals("2 John")) {

            database_toUse = "NewTestament.db";
        } else if (s.equals("Ruth") || s.equals("Song of Solomon") || s.equals("Lamentations")
                || s.equals("Joel") || s.equals("Amos") || s.equals("Obadiah")
                || s.equals("Jonah") || s.equals("Micah") || s.equals("Nahum")
                || s.equals("Habakkuk") || s.equals("Zephaniah") || s.equals("Hosea")
                || s.equals("Haggai") || s.equals("Malachi") || s.equals("Joshua")){

            database_toUse = "OldTestament.db";
        } else {
            database_toUse = "Appropriate";
        }
        return database_toUse;
    }
}
