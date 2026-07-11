package com.example.autoskip;

import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public class AutoSkipHelper {

    public static void processRootNode(AccessibilityNodeInfo rootNode) {
        if (rootNode == null) return;
        clickNodeByText(rootNode, "Skip ad");
        clickNodeByText(rootNode, "Skip ads");
        clickNodeByText(rootNode, "Skip Ad");
    }

    private static void clickNodeByText(AccessibilityNodeInfo node, String text) {
        if (node == null) return;

        CharSequence nodeText = node.getText();
        CharSequence nodeDesc = node.getContentDescription();
        
        if ((nodeText != null && nodeText.toString().equalsIgnoreCase(text)) || 
            (nodeDesc != null && nodeDesc.toString().equalsIgnoreCase(text))) {
            
            if (node.isClickable()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            } else {
                AccessibilityNodeInfo parent = node.getParent();
                if (parent != null && parent.isClickable()) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            clickNodeByText(node.getChild(i), text);
        }
    }
}
