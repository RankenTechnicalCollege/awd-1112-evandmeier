package edu.ranken.emeier.simpleasynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;
    private int mProgressMax;

    public SimpleAsyncTask(TextView tv, ProgressBar pb) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pb);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Random r = new Random();
        int n = r.nextInt(11);
        mProgressMax = (n * 200);

        mProgressBar.get().setMax(mProgressMax);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            long elapsedTime;
            // get the current time before the loop begins
            final long startTime = SystemClock.elapsedRealtime();

            do {
                Thread.sleep(16);

                // set the value of the total time that has passed since the loop began
                elapsedTime = SystemClock.elapsedRealtime() - startTime;

                //
                int progress = (int) elapsedTime;
                publishProgress(progress);
            } while (elapsedTime < mProgressMax);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return "Awake at last after sleeping for " + mProgressMax + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        mProgressBar.get().setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        mTextView.get().setText(s);
    }
}
