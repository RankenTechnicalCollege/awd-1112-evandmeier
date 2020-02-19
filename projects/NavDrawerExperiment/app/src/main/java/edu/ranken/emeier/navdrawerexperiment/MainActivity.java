package edu.ranken.emeier.navdrawerexperiment;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get widgets
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        // enable the action bar
        setSupportActionBar(toolbar);

        // add drawer toggle buttons
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                                        this,
                                                drawer,
                                                toolbar,
                                                R.string.nav_drawer_open,
                                                R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // register a listener for the drawer menu items
        navView.setNavigationItemSelectedListener((MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    displayToast("Home");
                    drawer.closeDrawers();
                    return true;
                case R.id.nav_login:
                    displayToast("Login");
                    drawer.closeDrawers();
                    return true;
                case R.id.nav_settings:
                    displayToast("Settings");
                    drawer.closeDrawers();
                    return true;
            }

            return false;
        });
    }

    private void displayToast(String message) {
        Toast.makeText(this, String.format("You selected: %s", message), Toast.LENGTH_SHORT).show();
    }
}
