package com.example.maksimsastratenko.wikipediareader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public static String wikiUrlENG = "https://en.wikipedia.org/wiki/Special:Random";
    public static String wikiUrlRUS = "https://ru.wikipedia.org/wiki/Special:Random";
    public static String wikiUrlLAT = "https://lv.wikipedia.org/wiki/Special:Random";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RadioGroup langRG = (RadioGroup) findViewById(R.id.langRadioGroup);
        langRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:

                        break;
                    case R.id.englishCheckbox:
                        MainActivity.currentLanguage = "ENG";
                        break;
                    case R.id.russianCheckbox:
                        MainActivity.currentLanguage = "RUS";
                        break;
                    case R.id.latvianCheckbox:
                        MainActivity.currentLanguage = "LAT";
                        break;

                    default:
                        break;
                }
            }
        });
    }

    }
