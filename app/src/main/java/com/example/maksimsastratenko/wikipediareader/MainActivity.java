package com.example.maksimsastratenko.wikipediareader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button generateLinkButton;
    private Button favorites;
    private Button share;
    public String currentUrl;
    public String currentTitle;
    //bad solution
    public static Map<String, String> favoritesList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        share = (Button) findViewById(R.id.shareButton);
        generateLinkButton = (Button) findViewById(R.id.generateLinkButton);
        favorites = (Button) findViewById(R.id.addToFavoritesButton);
        webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://en.wikipedia.org/wiki/Special:Random");

        webView.setWebViewClient(new WebViewClient() {

            // current url when webpage loading.. finish
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentUrl = url;
                currentTitle = view.getTitle();
            }
        });


        addListenerOnButton();
    }

    public void addListenerOnButton() {
        generateLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://en.wikipedia.org/wiki/Special:Random");
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for uniqueness of the article
                if (favoritesList.isEmpty()) {
                    favoritesList.put(currentTitle, currentUrl);
                } else {
                    for (Map.Entry<String, String> entry : favoritesList.entrySet()) {
                        if (entry.getKey() != currentTitle) {
                            favoritesList.put(currentTitle, currentUrl);
                            //Toast.makeText(MainActivity.this, "Adding to favorites...", Toast.LENGTH_SHORT).show();
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
                String shareBody = "Your body here";
                String shareSub = "Your Subject here";
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                startActivity(Intent.createChooser(intent, "Share Using"));
            }
        });
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
