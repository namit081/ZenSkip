package com.example.autoskip;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
public class StatsManagerTest {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        context.getSharedPreferences("ZenSkipStats", Context.MODE_PRIVATE).edit().clear().commit();
    }

    @Test
    public void testSkipCounting() {
        assertEquals(0, StatsManager.getTotalSkips(context));
        
        StatsManager.recordSkip(context, null);
        StatsManager.recordSkip(context, null);
        
        assertEquals(2, StatsManager.getTotalSkips(context));
        assertEquals(2, StatsManager.getTodayCount(context));
        assertEquals(2, StatsManager.getMonthCount(context));
    }

    @Test
    public void testLogStorageAndCleanup() throws Exception {
        for (int i = 0; i < 15; i++) {
            File fakeScreenshot = new File(context.getFilesDir(), "fake_" + i + ".png");
            fakeScreenshot.createNewFile();
            StatsManager.recordSkip(context, fakeScreenshot.getAbsolutePath());
        }
        
        List<StatsManager.SkipLog> logs = StatsManager.getLogs(context);
        assertEquals(10, logs.size());
    }
}
