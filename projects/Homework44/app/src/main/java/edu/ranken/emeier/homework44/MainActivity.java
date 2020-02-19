package edu.ranken.emeier.homework44;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayDonutScreen(View view) {
        Intent intent = new Intent(this, DonutActivity.class);

        startActivity(intent);
    }

    public void displayFroyoScreen(View view) {
        Intent intent = new Intent(this, FroyoActivity.class);

        startActivity(intent);
    }

    public void displayIcecreamScreen(View view) {
        Intent intent = new Intent(this, IcecreamActivity.class);

        startActivity(intent);
    }
}