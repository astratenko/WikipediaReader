package com.example.maksimsastratenko.wikipediareader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner languageSpinner;
    private WebView webView;
    private Button generateLinkButton;
    private Button favorites;
    private Button share;
    private String currentUrl;
    private String currentTitle;
    //TODO add DB
    public static Map<String, String> favoritesList = new HashMap<>();
    private String wikiUrlENG = "https://en.wikipedia.org/wiki/Special:Random";
    private String wikiUrlRUS = "https://ru.wikipedia.org/wiki/Special:Random";
    private String wikiUrlLAT = "https://lv.wikipedia.org/wiki/Special:Random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        share = (Button) findViewById(R.id.shareButton);
        generateLinkButton = (Button) findViewById(R.id.generateLinkButton);
        favorites = (Button) findViewById(R.id.addToFavoritesButton);
        webView = (WebView) findViewById(R.id.webView);
        languageSpinner = (Spinner) findViewById(R.id.languageSpinner);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(wikiUrlENG);

        webView.setWebViewClient(new WebViewClient() {

            // current url when webpage loading.. finish
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentUrl = url;
                currentTitle = view.getTitle();
            }
        });

        //language dropdown menu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.languages));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reloadPage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addListenerOnButton();
    }

    public void addListenerOnButton() {
        generateLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPage();
                //webView.loadUrl(wikiUrlENG);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for uniqueness of the article
                if (favoritesList.isEmpty()) {
                    favoritesList.put(currentTitle, currentUrl);
                    Toast.makeText(MainActivity.this, "Adding to favorites...", Toast.LENGTH_SHORT).show();
                } else {
                    for (Map.Entry<String, String> entry : favoritesList.entrySet()) {
                        if (entry.getKey() != currentTitle) {
                            favoritesList.put(currentTitle, currentUrl);
                            Toast.makeText(MainActivity.this, "Adding to favorites...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Body";
                String shareSub = "Subject";
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                startActivity(Intent.createChooser(intent, "Share Using"));
            }
        });
    }

    //Reloads the page depending on the language selected
    private void reloadPage() {
        String selectedLanguage = languageSpinner.getSelectedItem().toString();
        if (selectedLanguage.equals("ENG")) {
            webView.loadUrl(wikiUrlENG);
        } else if (selectedLanguage.equals("RUS")) {
            webView.loadUrl(wikiUrlRUS);
        } else if (selectedLanguage.equals("LAT")) {
            webView.loadUrl(wikiUrlLAT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            case R.id.main_favorites:
                Intent favoritesIntent = new Intent(this, FavoritesActivity.class);
                this.startActivity(favoritesIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
