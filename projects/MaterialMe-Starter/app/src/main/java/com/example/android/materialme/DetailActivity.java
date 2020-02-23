package com.example.android.materialme;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView sportsTitle = findViewById(R.id.titleDetail);
        TextView sportsLeague = findViewById(R.id.league_name);
        TextView sportsTeams = findViewById(R.id.teams);
        ImageView sportsImage = findViewById(R.id.sportsImageDetail);

        sportsTitle.setText(getIntent().getStringExtra("title"));
        sportsLeague.setText(getIntent().getStringExtra("league"));
        sportsTeams.setText(getIntent().getStringExtra("teams"));
        sportsImage.setImageResource(getIntent().getIntExtra("image_resource", R.drawable.img_badminton));
    }
}
