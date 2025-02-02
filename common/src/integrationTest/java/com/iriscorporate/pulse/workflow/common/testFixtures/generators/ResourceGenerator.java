package com.iriscorporate.pulse.workflow.common.testFixtures.generators;


import java.time.OffsetDateTime;


public class ResourceGenerator {

    protected OffsetDateTime beforeCreation;
    protected OffsetDateTime afterCreation;

    protected void registerCreationStart() {
        if (beforeCreation == null) {
            beforeCreation = OffsetDateTime.now();
        }
    }

    protected void registerCreationEnd() {
        afterCreation = OffsetDateTime.now();
    }


}
