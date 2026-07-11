package com.example.autoskip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Button;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);
        
        TextView tv = new TextView(this);
        tv.setText("ZenSkip\n\nPlease enable the Accessibility Service in Settings to allow the app to skip ads silently.");
        tv.setTextSize(18f);
        
        Button btn = new Button(this);
        btn.setText("Open Accessibility Settings");
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });
        
        layout.addView(tv);
        layout.addView(btn);
        
        setContentView(layout);
    }
}
