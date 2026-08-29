package com.example.autoskip;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AutoSkipHelperTest {

    // Variation 1: Exact matches
    @Test
    public void testExactMatchSkipAd() {
        assertTrue(AutoSkipHelper.isSkipText("skip ad"));
        assertTrue(AutoSkipHelper.isSkipText("skip"));
    }

    // Variation 2: Case insensitivity
    @Test
    public void testCaseInsensitiveSkipAd() {
        assertTrue(AutoSkipHelper.isSkipText("Skip Ad"));
        assertTrue(AutoSkipHelper.isSkipText("SKIP AD"));
        assertTrue(AutoSkipHelper.isSkipText("SkIp"));
    }

    // Variation 3: Whitespace padding
    @Test
    public void testWhitespacePadding() {
        assertTrue(AutoSkipHelper.isSkipText("  Skip ad  "));
        assertTrue(AutoSkipHelper.isSkipText("\tSkip\n"));
    }

    // Variation 4: Plural "ads"
    @Test
    public void testSkipAdsVariation() {
        assertTrue(AutoSkipHelper.isSkipText("Skip ads"));
        assertTrue(AutoSkipHelper.isSkipText("skip ADS"));
    }

    // Variation 5: "Skip video" variant
    @Test
    public void testSkipVideoVariation() {
        assertTrue(AutoSkipHelper.isSkipText("Skip video"));
        assertTrue(AutoSkipHelper.isSkipText("SKIP VIDEO"));
    }

    // Variation 6: Punctuation handling
    @Test
    public void testPunctuationHandling() {
        assertTrue("Should handle exclamation marks", AutoSkipHelper.isSkipText("Skip!"));
        assertTrue("Should handle periods", AutoSkipHelper.isSkipText("Skip ad."));
        assertTrue("Should handle symbols", AutoSkipHelper.isSkipText("Skip >>"));
        assertTrue("Should handle hyphens", AutoSkipHelper.isSkipText("-Skip-"));
    }

    // Variation 7: Null handling
    @Test
    public void testNullInput() {
        assertFalse("Null string should fail gracefully", AutoSkipHelper.isSkipText(null));
    }

    // Variation 8: Empty and blank strings
    @Test
    public void testEmptyAndBlankStrings() {
        assertFalse(AutoSkipHelper.isSkipText(""));
        assertFalse(AutoSkipHelper.isSkipText("   "));
        assertFalse(AutoSkipHelper.isSkipText("\n\t"));
    }

    // Variation 9: False positives strictly ignored
    @Test
    public void testNegativeFalsePositives() {
        assertFalse(AutoSkipHelper.isSkipText("Do not skip"));
        assertFalse(AutoSkipHelper.isSkipText("Skip to the next long video")); // Doesn't match EXACT "skip" or contains "skip ad"/"skip video"
        assertFalse(AutoSkipHelper.isSkipText("Something else entirely"));
    }

    // Variation 10: Numbers and random characters
    @Test
    public void testNumbersAndRandom() {
        assertFalse(AutoSkipHelper.isSkipText("12345"));
        assertFalse(AutoSkipHelper.isSkipText("Skip in 5")); // Will become "skip in 5" which does not equal "skip" or contain "skip ad"
        assertTrue("Should match skip ad with a number next to it if joined, wait no, 'skip ad 5' contains 'skip ad'", AutoSkipHelper.isSkipText("Skip ad 5"));
    }
}
