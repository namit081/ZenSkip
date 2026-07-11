package com.example.autoskip;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatsManager {
    private static final String PREFS_NAME = "ZenSkipStats";
    private static final String KEY_TOTAL_SKIPS = "total_skips";
    private static final String KEY_LOGS = "skip_logs";
    private static final int MAX_LOGS = 10;

    public static class SkipLog {
        public long timestamp;
        public String screenshotPath; // Can be null if not taken
        
        public SkipLog(long timestamp, String screenshotPath) {
            this.timestamp = timestamp;
            this.screenshotPath = screenshotPath;
        }
    }

    public static void recordSkip(Context context, String screenshotPath) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        
        // Increment total skips
        int total = prefs.getInt(KEY_TOTAL_SKIPS, 0);
        prefs.edit().putInt(KEY_TOTAL_SKIPS, total + 1).apply();
        incrementCounters(context);
        
        // Save log
        List<SkipLog> logs = getLogs(context);
        logs.add(0, new SkipLog(System.currentTimeMillis(), screenshotPath));
        if (logs.size() > MAX_LOGS) {
            // Remove old screenshots if they exist
            for (int i = MAX_LOGS; i < logs.size(); i++) {
                String path = logs.get(i).screenshotPath;
                if (path != null) {
                    new java.io.File(path).delete();
                }
            }
            logs = logs.subList(0, MAX_LOGS);
        }
        
        saveLogs(context, logs);
    }
    
    public static int getTotalSkips(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt(KEY_TOTAL_SKIPS, 0);
    }
    
    public static int getSkipsToday(Context context) {
        return getSkipsSince(context, getStartOfDay());
    }
    
    public static int getSkipsThisMonth(Context context) {
        return getSkipsSince(context, getStartOfMonth());
    }
    
    private static int getSkipsSince(Context context, long sinceTimestamp) {
        // A proper implementation would require storing every skip timestamp in a database.
        // For simplicity, we just count how many of the LAST 10 logs are within this time.
        // Wait, if we want true daily/monthly stats, we need to track them individually.
        return 0; // Will refactor this to store counts properly
    }

    // Refactored daily/monthly tracking
    public static void incrementCounters(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        
        String todayKey = "skips_day_" + new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date());
        String monthKey = "skips_month_" + new SimpleDateFormat("yyyyMM", Locale.US).format(new Date());
        
        prefs.edit()
            .putInt(todayKey, prefs.getInt(todayKey, 0) + 1)
            .putInt(monthKey, prefs.getInt(monthKey, 0) + 1)
            .apply();
    }
    
    public static int getTodayCount(Context context) {
        String todayKey = "skips_day_" + new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date());
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt(todayKey, 0);
    }
    
    public static int getMonthCount(Context context) {
        String monthKey = "skips_month_" + new SimpleDateFormat("yyyyMM", Locale.US).format(new Date());
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt(monthKey, 0);
    }

    public static List<SkipLog> getLogs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonStr = prefs.getString(KEY_LOGS, "[]");
        List<SkipLog> logs = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonStr);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String path = obj.has("path") ? obj.getString("path") : null;
                logs.add(new SkipLog(obj.getLong("time"), path));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return logs;
    }

    private static void saveLogs(Context context, List<SkipLog> logs) {
        JSONArray array = new JSONArray();
        try {
            for (SkipLog log : logs) {
                JSONObject obj = new JSONObject();
                obj.put("time", log.timestamp);
                if (log.screenshotPath != null) {
                    obj.put("path", log.screenshotPath);
                }
                array.put(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putString(KEY_LOGS, array.toString()).apply();
    }

    private static long getStartOfDay() { return 0; }
    private static long getStartOfMonth() { return 0; }
}
