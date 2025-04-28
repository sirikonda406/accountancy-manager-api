package com.ca.account.manager.common.datasource;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    static final String DEFAULT_TENANT = "66bcbd805157e4e36e0b0b51";

    @Override
    public String resolveCurrentTenantIdentifier() {
        return DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
