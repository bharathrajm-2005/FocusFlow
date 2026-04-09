package com.focusflow.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.focusflow.app.R;
import com.focusflow.app.utils.SharedPrefHelper;

public class MainActivity extends AppCompatActivity {

    private TextView tvStreak;
    private TextView tvPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isDarkMode = SharedPrefHelper.getBoolean(this, "darkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStreak = findViewById(R.id.tvStreak);
        tvPoints = findViewById(R.id.tvPoints);

        Button btnStartSession = findViewById(R.id.btnStartSession);
        Button btnViewStats = findViewById(R.id.btnViewStats);

        btnStartSession.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FocusSessionActivity.class);
            startActivity(intent);
        });

        btnViewStats.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStats();
    }

    private void updateStats() {
        int streak = SharedPrefHelper.getStreak(this);
        int points = SharedPrefHelper.getPoints(this);

        tvStreak.setText("🔥 Streak: " + streak + " days");
        tvPoints.setText("⭐ Points: " + points);
    }
}
