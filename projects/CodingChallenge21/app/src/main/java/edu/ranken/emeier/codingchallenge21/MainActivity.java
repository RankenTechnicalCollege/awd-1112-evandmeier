package edu.ranken.emeier.codingchallenge21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_HEADER = "edu.ranken.emeier.codingchallenge21.EXTRA_HEADER";
    public static final String EXTRA_ARTICLE = "edu.ranken.emeier.codingchallenge21.EXTRA_ARTICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void displayFirstArticle(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_HEADER, getString(R.string.article_1_header));
        intent.putExtra(EXTRA_ARTICLE, getString(R.string.article_1_body));

        startActivity(intent);
    }

    public void displaySecondArticle(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_HEADER, getString(R.string.article_2_header));
        intent.putExtra(EXTRA_ARTICLE, getString(R.string.article_2_body));

        startActivity(intent);
    }

    public void displayThirdArticle(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_HEADER, getString(R.string.article_3_header));
        intent.putExtra(EXTRA_ARTICLE, getString(R.string.article_3_body));

        startActivity(intent);
    }
}
