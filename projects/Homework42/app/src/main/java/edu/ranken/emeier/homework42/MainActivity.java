package edu.ranken.emeier.homework42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox[] checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxes = new CheckBox[5];
        checkBoxes[0] = findViewById(R.id.check_chocolate);
        checkBoxes[1] = findViewById(R.id.check_sprinkles);
        checkBoxes[2] = findViewById(R.id.check_nuts);
        checkBoxes[3] = findViewById(R.id.check_cherries);
        checkBoxes[4] = findViewById(R.id.check_cookie);
    }

    public void showToast(View view) {
        // checks whether any toppings were selected
        //boolean toppingsSelected = false;

        // holds the output of the toast
        StringBuilder output = new StringBuilder("Toppings: ");
        int initialLength = output.length();

        // iterate through the checkbox array
        for(CheckBox checkBox : checkBoxes) {
            // check if the current checkbox is checked
            // if so, append the output string
            if (checkBox.isChecked()) {
                output.append(checkBox.getText().toString()).append(" + ");
                //toppingsSelected = true;
            }
        }

        if (output.length() > initialLength) {
            output.setLength(output.length() - 3);
            Toast.makeText(getApplicationContext(), output.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Toppings Selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
