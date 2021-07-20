package org.yeastrc.limelight.xml.philosopher.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhilosopherParsingUtilsTest {

    @Test
    void getScanNumberFromSpectrumId() {
        try {
            assertEquals(60, PhilosopherParsingUtils.getScanNumberFromSpectrumId("QEP2_2018_0812_AZ_029_az735_AZ.00060.00060.3"));
            assertEquals(1020, PhilosopherParsingUtils.getScanNumberFromSpectrumId("QEP2_2018_0812_AZ_029_az735_AZ.foo.bar.1020.1020.1"));
        } catch(Throwable t) {
            fail();
        }
    }

}