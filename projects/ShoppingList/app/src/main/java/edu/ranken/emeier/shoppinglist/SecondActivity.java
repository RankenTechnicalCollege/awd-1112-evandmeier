package edu.ranken.emeier.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_ITEM = "edu.ranken.emeier.shoppinglist.SELECTED_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void selectItem(View view) {
        Button button = (Button) view;

        String selectedItem = button.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_SELECTED_ITEM, selectedItem);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
