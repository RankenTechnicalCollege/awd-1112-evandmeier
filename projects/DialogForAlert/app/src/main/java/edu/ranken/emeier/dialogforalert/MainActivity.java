package edu.ranken.emeier.dialogforalert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowAlert(View view) {
        // create alert dialog
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);

        // set the dialog title and message
        myAlertBuilder.setTitle(R.string.alert_title);
        myAlertBuilder.setMessage(R.string.alert_message);

        // add the dialog buttons
        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            // user clicked OK button.
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Pressed OK", Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // user cancelled the dialog
                Toast.makeText(getApplicationContext(), "Pressed Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        // create and show the alertdialog
        myAlertBuilder.show();
    }
}
