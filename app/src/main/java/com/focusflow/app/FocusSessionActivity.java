package com.focusflow.app;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.focusflow.app.R;
import com.focusflow.app.utils.SharedPrefHelper;
import com.focusflow.app.utils.TimerHelper;

import java.util.Locale;
import java.util.Random;

public class FocusSessionActivity extends AppCompatActivity {

    private TextView tvTimer;
    private TextView tvQuote;
    private ProgressBar sessionProgressBar;
    private TimerHelper timerHelper;

    private static final long SESSION_DURATION_MS = 1800000L; // 30 minutes

    private String[] quotes = {
            "Stay focused, you're doing great!",
            "Success is built one session at a time.",
            "No distractions. Only goals.",
            "Discipline beats motivation."
    };

    private Handler motivationHandler = new Handler();
    private Runnable motivationRunnable = new Runnable() {
        @Override
        public void run() {
            Random random = new Random();
            tvQuote.setText(quotes[random.nextInt(quotes.length)]);
            motivationHandler.postDelayed(this, 300000); // 5 mins
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_session);

        tvTimer = findViewById(R.id.tvTimer);
        tvQuote = findViewById(R.id.tvQuote);
        sessionProgressBar = findViewById(R.id.sessionProgressBar);
        
        Button btnStopSession = findViewById(R.id.btnStopSession);
        btnStopSession.setOnClickListener(v -> stopSession());

        timerHelper = new TimerHelper();
        startSession();
        
        motivationHandler.post(motivationRunnable);
    }

    private void startSession() {
        timerHelper.startTimer(SESSION_DURATION_MS, 1000L, new TimerHelper.TimerCallback() {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerText(millisUntilFinished);
                
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                sessionProgressBar.setProgress(secondsRemaining);
            }

            @Override
            public void onFinish() {
                completeSession();
            }
        });
    }

    private void updateTimerText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimer.setText(timeFormatted);
    }

    private void stopSession() {
        if (timerHelper != null) {
            timerHelper.cancelTimer();
        }
        finish();
    }

    private void completeSession() {
        int streak = SharedPrefHelper.getStreak(this);
        int points = SharedPrefHelper.getPoints(this);
        int totalSessions = SharedPrefHelper.getTotalSessions(this);
        int totalTime = SharedPrefHelper.getTotalTime(this);

        SharedPrefHelper.setStreak(this, streak + 1);
        SharedPrefHelper.setPoints(this, points + 10);
        SharedPrefHelper.setTotalSessions(this, totalSessions + 1);
        SharedPrefHelper.setTotalTime(this, totalTime + 30);
        
        int sessionsToday = SharedPrefHelper.getInt(this, "sessionsToday", 0);
        SharedPrefHelper.setInt(this, "sessionsToday", sessionsToday + 1);

        Toast.makeText(this, "Session Complete! 🎉", Toast.LENGTH_SHORT).show();
        
        try {
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(ToneGenerator.TONE_PROP_ACK, 1000); // 1 second chime
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Stay focused! Avoid using other apps \uD83D\uDEAB", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHelper != null) {
            timerHelper.cancelTimer();
        }
        motivationHandler.removeCallbacks(motivationRunnable);
    }

    @Override
    public void onBackPressed() {
        stopSession();
    }
}
