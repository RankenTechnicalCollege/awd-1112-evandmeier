package edu.ranken.emeier.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int ITEM_REQUEST = 1;

    // Array of the TextViews that hold the items
    private TextView[] textViews;
    // Add item button
    private Button addItemButton;
    // The amount of items in the array
    private int count = 0;

    private EditText mEditTextLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViews = new TextView[10];
        textViews[0] = findViewById(R.id.item0);
        textViews[1] = findViewById(R.id.item1);
        textViews[2] = findViewById(R.id.item2);
        textViews[3] = findViewById(R.id.item3);
        textViews[4] = findViewById(R.id.item4);
        textViews[5] = findViewById(R.id.item5);
        textViews[6] = findViewById(R.id.item6);
        textViews[7] = findViewById(R.id.item7);
        textViews[8] = findViewById(R.id.item8);
        textViews[9] = findViewById(R.id.item9);

        addItemButton = findViewById(R.id.addItemButton);
        mEditTextLocation = findViewById(R.id.edit_text_search);
        Log.i("MainActivity", "MainActivity layout is complete");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String[] items = new String[count];
        for (int i = 0; i < items.length; ++i) {
            items[i] = textViews[i].getText().toString();
        }

        outState.putStringArray("shopping_items", items);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String[] savedItems = savedInstanceState.getStringArray("shopping_items");
        if (savedItems != null) {
            count = savedItems.length;

            for (int i = 0; i < textViews.length; ++i) {
                textViews[i].setText(savedItems[i]);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (count < textViews.length) {
                    String item = data.getStringExtra(SecondActivity.EXTRA_SELECTED_ITEM);
                    textViews[count].setText(item);
                    ++count;

                    if (count >= textViews.length) {
                        Toast toast = Toast.makeText(this, "Shopping List Full!", Toast.LENGTH_LONG);
                        toast.show();

                        addItemButton.setVisibility(View.INVISIBLE);
                    }
                    // in theory, we should never get to this else statement...
                } else {
                    Toast toast = Toast.makeText(this, "Shopping List Full!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    // The button that method that loads the second activity
    public void addItem(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, 1);
    }

    public void openLocation(View view) {
        String loc = mEditTextLocation.getText().toString();

        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
}