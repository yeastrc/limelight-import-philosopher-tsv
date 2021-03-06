package org.yeastrc.limelight.xml.philosopher.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MassUtilsTest {

    @Test
    void sameScaleEquals() {
        assertTrue(MassUtils.sameScaleEquals(new BigDecimal("10.5124"), new BigDecimal("10.512421")));
        assertTrue(MassUtils.sameScaleEquals(new BigDecimal("57.021464"), new BigDecimal("57.0215")));
        assertFalse(MassUtils.sameScaleEquals(new BigDecimal("10.5224"), new BigDecimal("10.512421")));
    }
}
