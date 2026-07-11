package com.example.autoskip;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class AutoSkipService extends AccessibilityService {

    private long lastSkipTime = 0;
    
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AutoSkipHelper.isServiceRunning = true;
    }

    @Override
    public boolean onUnbind(android.content.Intent intent) {
        AutoSkipHelper.isServiceRunning = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) {
            return;
        }

        // Only process if it's a window content change or window state change
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED &&
            event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return;
        }

        // Optimization: Only run for YouTube to save battery
        CharSequence packageName = event.getPackageName();
        if (packageName != null && !packageName.toString().contains("youtube")) {
            return;
        }

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) return;

        // Search and click "Skip ad" or "Skip ads"
        boolean clicked = AutoSkipHelper.processRootNode(rootNode);
        rootNode.recycle();

        if (clicked) {
            long now = System.currentTimeMillis();
            if (now - lastSkipTime > 5000) { // debounce 5 seconds
                lastSkipTime = now;
                takeScreenshotAndLog();
            }
        }
    }
    
    private void takeScreenshotAndLog() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            takeScreenshot(android.view.Display.DEFAULT_DISPLAY, getApplicationContext().getMainExecutor(), new TakeScreenshotCallback() {
                @Override
                public void onSuccess(ScreenshotResult screenshotResult) {
                    try {
                        android.graphics.Bitmap bitmap = android.graphics.Bitmap.wrapHardwareBuffer(screenshotResult.getHardwareBuffer(), screenshotResult.getColorSpace());
                        if (bitmap != null) {
                            java.io.File dir = getFilesDir();
                            java.io.File file = new java.io.File(dir, "screenshot_" + System.currentTimeMillis() + ".png");
                            java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
                            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 50, fos);
                            fos.close();
                            StatsManager.recordSkip(getApplicationContext(), file.getAbsolutePath());
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    StatsManager.recordSkip(getApplicationContext(), null);
                }

                @Override
                public void onFailure(int i) {
                    StatsManager.recordSkip(getApplicationContext(), null);
                }
            });
        } else {
            StatsManager.recordSkip(getApplicationContext(), null);
        }
    }

    @Override
    public void onInterrupt() {
    }
}
