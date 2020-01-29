package edu.ranken.emeier.codingchallenge21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView mArticleHeader;
    private TextView mArticleBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mArticleHeader = findViewById(R.id.article_heading);
        mArticleBody = findViewById(R.id.article_body);

        Intent intent = getIntent();
        String heading = intent.getStringExtra(MainActivity.EXTRA_HEADER);
        String body = intent.getStringExtra(MainActivity.EXTRA_ARTICLE);

        mArticleHeader.setText(heading);
        mArticleBody.setText(body);
    }
}