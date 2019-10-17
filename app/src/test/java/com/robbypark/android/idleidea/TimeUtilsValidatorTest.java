package com.robbypark.android.idleidea;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeUtilsValidatorTest {
    @Test
    public void timeVallidator_simpleTimeAgo() throws Exception {
        String test = TimeUtils.timeAgo(100000000);
        assertEquals("1d3h", test);
    }

    @Test
    public void timeVallidator_negativeTimeAgo() throws Exception {
        String test = TimeUtils.timeAgo(-100);
        assertEquals("<1hr", test);
    }

    @Test
    public void timeVallidator_subOneHourTimeAgo() throws Exception {
        String test = TimeUtils.timeAgo(10000);
        assertEquals("<1hr", test);
    }

    @Test
    public void timeVallidator_simpleTimeString() throws Exception {
        String test = TimeUtils.getTimeString(0, 100000000);
        assertEquals("Dec 31, 1969 - Jan 1, 1970. Total: 1d3h", test);
    }
}
