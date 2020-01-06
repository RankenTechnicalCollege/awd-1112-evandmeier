package com.murach.ch10_ex4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private final String URL_STRING = "http://www.murach.com/images/andp.jpg";
    private final String FILENAME = "android_book.jpg";
    private ImageView fileImageView; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fileImageView = (ImageView) findViewById(R.id.fileImageView);
        
        new DownloadFile().execute();
    }
    
    class DownloadFile extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            // download and write the file
            try{
                // get the URL object
                URL url = new URL(URL_STRING);

                // get the input stream
                InputStream in = url.openStream();
                
                // get the output stream
                FileOutputStream out = 
                        openFileOutput(FILENAME, Context.MODE_PRIVATE);

                // read input and write output
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                while (bytesRead != -1)
                {
                    out.write(buffer, 0, bytesRead);
                    bytesRead = in.read(buffer);
                }
                out.close();
                in.close();
                
                // return a message
                return "File downloaded";
            }
            catch (IOException e) {
                return "Error: " + e.toString();
            }
        }
        
        @Override
        protected void onPostExecute(String message) {
            Log.d("Test", message);
            readFile();
        }                
    }
    
    private void readFile() {
        try {
            FileInputStream in = openFileInput(FILENAME);
            Drawable image = Drawable.createFromStream(in, FILENAME);
            fileImageView.setImageDrawable(image);
            Log.d("Test", "File read");            
        } 
        catch (Exception e) {
        	Log.e("Test", "Error: " + e.toString());
        }
    }
}