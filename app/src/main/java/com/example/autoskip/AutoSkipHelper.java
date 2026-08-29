package com.example.autoskip;

import android.view.accessibility.AccessibilityNodeInfo;

public class AutoSkipHelper {

    public static boolean isServiceRunning = false;

    public static boolean processRootNode(AccessibilityNodeInfo rootNode) {
        if (rootNode == null) return false;
        
        return findAndClickSkipButton(rootNode);
    }

    private static boolean findAndClickSkipButton(AccessibilityNodeInfo node) {
        if (node == null) return false;

        if (isSkipButton(node)) {
            if (tryClick(node)) return true;
        }

        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                if (findAndClickSkipButton(child)) {
                    child.recycle();
                    return true;
                }
                child.recycle();
            }
        }
        return false;
    }

    private static boolean isSkipButton(AccessibilityNodeInfo node) {
        CharSequence text = node.getText();
        if (text != null && isSkipText(text.toString())) return true;
        
        CharSequence desc = node.getContentDescription();
        if (desc != null && isSkipText(desc.toString())) return true;
        
        return false;
    }

    static boolean isSkipText(String s) {
        if (s == null) return false;
        String lower = s.toLowerCase().trim();
        // Remove punctuation to handle things like "Skip!", "Skip ad." or "Skip >"
        lower = lower.replaceAll("[^a-z0-9 ]", "").trim();
        
        return lower.contains("skip ad") || 
               lower.contains("skip video") || 
               lower.equals("skip");
    }

    private static boolean tryClick(AccessibilityNodeInfo node) {
        AccessibilityNodeInfo current = node;
        while (current != null) {
            if (current.isClickable()) {
                boolean result = current.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                if (current != node) {
                    current.recycle();
                }
                return result;
            }
            AccessibilityNodeInfo parent = current.getParent();
            if (current != node) {
                current.recycle();
            }
            current = parent;
        }
        return false;
    }
}
