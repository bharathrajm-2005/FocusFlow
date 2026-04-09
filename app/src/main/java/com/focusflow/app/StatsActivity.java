package com.focusflow.app;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.focusflow.app.R;
import com.focusflow.app.utils.SharedPrefHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StatsActivity extends AppCompatActivity {

    private TextView tvTotalSessions;
    private TextView tvTotalTime;
    private TextView tvStatsStreak;
    private TextView tvStatsPoints;
    private TextView tvDailyGoal;
    private ProgressBar progressDailyGoal;
    private Switch switchDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tvTotalSessions = findViewById(R.id.tvTotalSessions);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvStatsStreak = findViewById(R.id.tvStatsStreak);
        tvStatsPoints = findViewById(R.id.tvStatsPoints);
        tvDailyGoal = findViewById(R.id.tvDailyGoal);
        progressDailyGoal = findViewById(R.id.progressDailyGoal);
        switchDarkMode = findViewById(R.id.switchDarkMode);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        boolean isDarkMode = SharedPrefHelper.getBoolean(this, "darkMode", false);
        switchDarkMode.setChecked(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPrefHelper.setBoolean(this, "darkMode", isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStats();
    }

    private void loadStats() {
        int totalSessions = SharedPrefHelper.getTotalSessions(this);
        int totalTime = SharedPrefHelper.getTotalTime(this);
        int streak = SharedPrefHelper.getStreak(this);
        int points = SharedPrefHelper.getPoints(this);

        tvTotalSessions.setText(String.valueOf(totalSessions));
        tvTotalTime.setText(totalTime + " mins");
        tvStatsStreak.setText(streak + " days");
        tvStatsPoints.setText(String.valueOf(points));

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String savedDate = SharedPrefHelper.getString(this, "lastDate", "");

        if (!today.equals(savedDate)) {
            SharedPrefHelper.setInt(this, "sessionsToday", 0);
            SharedPrefHelper.setString(this, "lastDate", today);
        }

        int sessionsToday = SharedPrefHelper.getInt(this, "sessionsToday", 0);
        
        tvDailyGoal.setText("Today's Goal: " + sessionsToday + " / 3 sessions");
        progressDailyGoal.setProgress(sessionsToday);

        if (sessionsToday >= 3) {
            tvDailyGoal.setTextColor(Color.parseColor("#4CAF50")); // Green
            tvDailyGoal.setText("🎯 Daily Goal Reached!");
        } else {
            tvDailyGoal.setTextColor(tvTotalSessions.getCurrentTextColor());
        }
    }
}
