package edu.ranken.emeier.homework72;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class PageSourceLoader extends AsyncTaskLoader<String> {

    public PageSourceLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return null;
    }
}
