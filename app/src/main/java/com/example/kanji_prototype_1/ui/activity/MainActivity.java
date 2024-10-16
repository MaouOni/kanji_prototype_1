package com.example.kanji_prototype_1.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.kanji_prototype_1.R;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Drawer layout and hamburger button setup
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Set up the hamburger button to open the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, findViewById(R.id.toolbar),
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up the navigation listener using if-else structure
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_lecciones) {
                // Go to the KanjiDrawActivity for drawing lessons
                startActivity(new Intent(MainActivity.this, KanjiInputActivity.class));
            } else if (id == R.id.nav_busqueda) {
                // Go to the KanjiSearchActivity for searching Kanji
                startActivity(new Intent(MainActivity.this, KanjiSearchActivity.class));
            }
            return true;
        });

        // Link the config button to SettingsActivity
        ImageButton configButton = findViewById(R.id.config_button);
        configButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Link the busqueda_button to KanjiSearchActivity
        Button busquedaButton = findViewById(R.id.busqueda_button);
        busquedaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KanjiSearchActivity.class);
            startActivity(intent);
        });
    }
}
