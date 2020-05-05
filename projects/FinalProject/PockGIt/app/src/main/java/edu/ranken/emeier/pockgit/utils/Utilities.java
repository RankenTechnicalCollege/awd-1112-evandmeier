package edu.ranken.emeier.pockgit.utils;

import android.content.Context;
import android.widget.Toast;

public class Utilities {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
