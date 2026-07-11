package com.example.autoskip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private LinearLayout logContainer;
    private TextView statusText;
    private TextView statsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ScrollView scroll = new ScrollView(this);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);
        
        TextView title = new TextView(this);
        title.setText("🧘 ZenSkip");
        title.setTextSize(24f);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 30);
        
        statusText = new TextView(this);
        statusText.setTextSize(18f);
        statusText.setGravity(Gravity.CENTER);
        statusText.setPadding(0, 0, 0, 30);
        
        Button btn = new Button(this);
        btn.setText("Open Accessibility Settings");
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
        
        statsText = new TextView(this);
        statsText.setTextSize(16f);
        statsText.setPadding(0, 50, 0, 30);
        
        TextView logTitle = new TextView(this);
        logTitle.setText("Recent Skips");
        logTitle.setTextSize(18f);
        logTitle.setPadding(0, 50, 0, 10);
        
        logContainer = new LinearLayout(this);
        logContainer.setOrientation(LinearLayout.VERTICAL);
        
        layout.addView(title);
        layout.addView(statusText);
        layout.addView(btn);
        layout.addView(statsText);
        layout.addView(logTitle);
        layout.addView(logContainer);
        
        scroll.addView(layout);
        setContentView(scroll);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
    
    private void updateUI() {
        if (AutoSkipHelper.isServiceRunning) {
            statusText.setText("Service is Active 🟢");
            statusText.setTextColor(0xFF00AA00);
        } else {
            statusText.setText("Service is Inactive 🔴\n\nPlease enable it in Settings.");
            statusText.setTextColor(0xFFAA0000);
        }
        
        int total = StatsManager.getTotalSkips(this);
        int today = StatsManager.getTodayCount(this);
        int month = StatsManager.getMonthCount(this);
        
        statsText.setText(String.format(Locale.US, "📊 Stats:\nTotal Skips: %d\nThis Month: %d\nToday: %d", total, month, today));
        
        logContainer.removeAllViews();
        List<StatsManager.SkipLog> logs = StatsManager.getLogs(this);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - hh:mm:ss a", Locale.US);
        
        if (logs.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No skips recorded yet.");
            logContainer.addView(empty);
            return;
        }
        
        for (StatsManager.SkipLog log : logs) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.VERTICAL);
            row.setPadding(0, 20, 0, 20);
            
            TextView time = new TextView(this);
            time.setText("🕒 " + sdf.format(new Date(log.timestamp)));
            row.addView(time);
            
            if (log.screenshotPath != null) {
                java.io.File file = new java.io.File(log.screenshotPath);
                if (file.exists()) {
                    ImageView img = new ImageView(this);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    if (bitmap != null) {
                        img.setImageBitmap(bitmap);
                        img.setAdjustViewBounds(true);
                        img.setMaxHeight(400);
                        img.setPadding(0, 10, 0, 0);
                        row.addView(img);
                    }
                }
            }
            
            logContainer.addView(row);
        }
    }
}
