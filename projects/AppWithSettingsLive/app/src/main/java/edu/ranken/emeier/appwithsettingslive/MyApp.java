package edu.ranken.emeier.appwithsettingslive;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class MyApp extends Application {

    // constants
    public static final String KEY_PREF_EXAMPLE_SWITCH = "example_switch";

    /// fields
    private SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public boolean getSwitchPref() {
        return mPreferences.getBoolean(KEY_PREF_EXAMPLE_SWITCH, true);
    }
}
