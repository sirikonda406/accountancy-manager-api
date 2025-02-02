package com.iriscorporate.pulse.workflow.common.testFixtures;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TestingData {

    protected static BigInteger randomBigInteger() {
        return new BigInteger(130, new SecureRandom());
    }

}
