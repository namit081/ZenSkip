package com.example.autoskip;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class AutoSkipService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            AutoSkipHelper.processRootNode(rootNode);
            rootNode.recycle();
        }
    }

    @Override
    public void onInterrupt() {
    }
}
