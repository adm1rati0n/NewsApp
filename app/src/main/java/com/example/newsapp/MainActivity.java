package com.example.newsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    List<NewsModel> models = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewUser);
        databaseHelper = new DatabaseHelper(this);

        updateRecyclerView();
    }

    private void updateRecyclerView(){
        try (Cursor cursor = databaseHelper.getNewsData();) {
            int idColumnIndex = cursor.getColumnIndex(NewsDBContract.News._ID);
            int headerColumnIndex = cursor.getColumnIndex(NewsDBContract.News.COLUMN_TITLE);
            int mainTextColumnIndex = cursor.getColumnIndex(NewsDBContract.News.COLUMN_TEXT);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentHeader = cursor.getString(headerColumnIndex);
                String currentText = cursor.getString(mainTextColumnIndex);

                models.add(new NewsModel(currentID, currentHeader, currentText));
            }
        }

        for (NewsModel model : models) {
            Log.d("Data", model.getID() + " - " + model.getHeader());

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
            RecyclerViewAdapterUserNews adapter = new RecyclerViewAdapterUserNews(getApplicationContext(), models);
            recyclerView.setAdapter(adapter);
        }
    }

}