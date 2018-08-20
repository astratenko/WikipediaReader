package com.example.maksimsastratenko.wikipediareader;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO Change ArrayList to HashMap
        List<String> listArr = new ArrayList<>();
        for (Map.Entry<String, String> entry : MainActivity.favoritesList.entrySet()) {
            String string1 = entry.getKey() + " " + entry.getValue();
            listArr.add(string1);
        }
        printFavoritesList(listArr);

    }

    private void printFavoritesList(List<String> listArr) {

        list = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.article, listArr);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String val = (String) list.getItemAtPosition(position);
            }
        });
    }
}
