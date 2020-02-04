package edu.ranken.emeier.hot2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mWidthInput, mHeightInput;
    private TextView mAreaOutput, mPerimeterOutput, mErrorOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the member variables to their respective controls
        mWidthInput = findViewById(R.id.edit_width);
        mHeightInput = findViewById(R.id.edit_height);
        mAreaOutput = findViewById(R.id.text_area);
        mPerimeterOutput = findViewById(R.id.text_perimeter);
        mErrorOutput = findViewById(R.id.text_error);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the width and height to the bundle
        // we will attempt to parse the string values in the restore methods
        outState.putString("width", mWidthInput.getText().toString());
        outState.putString("height", mHeightInput.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String width = savedInstanceState.getString("width");
        String height = savedInstanceState.getString("height");

        makeCalculations(width, height);
    }

    public void displayResults(View view) {
        makeCalculations(mWidthInput.getText().toString(), mHeightInput.getText().toString());
    }

    private void makeCalculations(String widthEntry, String heightEntry) {
        double width, height;

        // clear current error message if necessary
        mErrorOutput.setText("");

        try {
            // if there is an empty entry
            if (widthEntry.isEmpty()
                    || heightEntry.isEmpty()) {
                throw new IllegalArgumentException("Make sure you enter two valid numbers!");
            }

            // get width and height entries
            width = Double.parseDouble(widthEntry);
            height = Double.parseDouble(heightEntry);

            // calculate area and perimeter
            double area = Calculations.calculateArea(width, height);
            double perimeter = Calculations.calculatePerimeter(width, height);

            // update screen
            mAreaOutput.setText(String.format("Area: %.2f sq ft", area));
            mPerimeterOutput.setText(String.format("Perimeter: %.2f ft", perimeter));

            Toast toast = Toast.makeText(getApplicationContext(), "Successfully made calculations!", Toast.LENGTH_LONG);
            toast.show();

        } catch(IllegalArgumentException e) {
            Log.e("MainActivity", "[Error] 1 or 2 empty entries!");

            // clear current area and perimeter values
            mAreaOutput.setText("");
            mPerimeterOutput.setText("");

            // display error message to users
            mErrorOutput.setText(e.getMessage());
        }
    }


}
