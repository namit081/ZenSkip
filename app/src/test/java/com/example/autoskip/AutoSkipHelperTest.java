package com.example.autoskip;

import android.view.accessibility.AccessibilityNodeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAccessibilityNodeInfo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
public class AutoSkipHelperTest {

    @Test
    public void testSkipAdClick() {
        AccessibilityNodeInfo rootNode = AccessibilityNodeInfo.obtain();
        ShadowAccessibilityNodeInfo shadowRoot = shadowOf(rootNode);
        
        AccessibilityNodeInfo irrelevantNode = AccessibilityNodeInfo.obtain();
        irrelevantNode.setText("Some random video title");
        irrelevantNode.setClickable(true);
        shadowRoot.addChild(irrelevantNode);
        
        AccessibilityNodeInfo skipNode = AccessibilityNodeInfo.obtain();
        skipNode.setText("Skip ad");
        skipNode.setClickable(true);
        shadowRoot.addChild(skipNode);
        
        AutoSkipHelper.processRootNode(rootNode);
        
        ShadowAccessibilityNodeInfo shadowSkip = shadowOf(skipNode);
        assertTrue("Skip ad node should have been clicked", 
            shadowSkip.getPerformedActions().contains(AccessibilityNodeInfo.ACTION_CLICK));
            
        ShadowAccessibilityNodeInfo shadowIrrelevant = shadowOf(irrelevantNode);
        assertFalse("Irrelevant node should NOT be clicked",
            shadowIrrelevant.getPerformedActions().contains(AccessibilityNodeInfo.ACTION_CLICK));
    }
}
