package com.example.autoskip;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AutoSkipHelperTest {

    @Test
    public void testSkipTextVariations() {
        String[] validTexts = {"Skip ad", "Skip Ad", "SKIP AD", "Skip ads", "Skip video", "skip", "Skip ", " skip "};
        for (String text : validTexts) {
            assertTrue("Should match text: " + text, AutoSkipHelper.isSkipText(text));
        }
    }

    @Test
    public void testInvalidTexts() {
        String[] invalidTexts = {"Do not skip", "Something else", "Skip to the next long video"};
        for (String text : invalidTexts) {
            assertFalse("Should NOT match text: " + text, AutoSkipHelper.isSkipText(text));
        }
    }
}
