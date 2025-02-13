package com.ca.account.manager.common.datasource;

import com.ca.account.manager.common.datasource.master.IndexDatabase;
import com.ca.account.manager.common.datasource.master.IndexDatabaseRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);


    private final Map<String, DataSource> TENANT_DATASOURCE_CACHE = new ConcurrentHashMap<>();

    @Autowired
    private DataSource masterDataSource;

    @Autowired
    private IndexDatabaseRepository indexDatabaseRepository;

    /**
     * Get a HikariDataSource for the given master tenant.
     * <p>
     * The HikariDataSource is created using the {@link DataSourceBuilder} and set with the
     * following properties:
     * <ul>
     * <li>driverClassName: the driver class name of the master tenant</li>
     * <li>username: the username of the master tenant</li>
     * <li>password: the password of the master tenant</li>
     * <li>url: the JDBC URL of the master tenant</li>
     * <li>type: the type of the datasource, which is a HikariDataSource</li>
     * </ul>
     * Additionally, the schema and pool name are set to the schema of the master tenant.
     * <p>
     * The method returns the HikariDataSource.
     *
     * @param masterTenant the master tenant
     * @return the HikariDataSource
     */
    private static HikariDataSource getHikariDataSource(IndexDatabase masterTenant) {
        try {
            LOGGER.info("Creating HikariDataSource for tenant: {}", masterTenant.getIdschema());

            // Extract tenant properties into local variables for clarity
            String schema = masterTenant.getIdschema();

            // Use a helper method to configure the data source
            HikariDataSource hikariDataSource = configureHikariDataSource(masterTenant);
            hikariDataSource.setSchema(schema);
            hikariDataSource.setPoolName(schema);

            return hikariDataSource;
        } catch (Exception ex) {
            LOGGER.error("Error creating HikariDataSource for tenant {}: {}", masterTenant.getIdschema(), ex.getMessage(), ex);
            throw new IllegalStateException("Failed to create HikariDataSource for tenant: " + masterTenant.getIdschema(), ex);
        }
    }

    private static HikariDataSource configureHikariDataSource(IndexDatabase masterTenant) {
        return DataSourceBuilder.create()
                .driverClassName(masterTenant.getIddriver())
                .username(masterTenant.getIdusername())
                .password(masterTenant.getIdpassword())
                .url(masterTenant.getIdurl())
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * Return a DataSource to use for the "any" tenant.
     * <p>
     * This method is used when the tenant is not specified.
     * <p>
     * It is used to obtain a connection to the database where the tenant is not specified.
     * <p>
     * In this case, we loop through all the master tenants and add them to the cache.
     * <p>
     * The first one is used as the default tenant.
     *
     * @return a DataSource to use for the "any" tenant.
     */
    @Override
    protected DataSource selectAnyDataSource() {
        if (TENANT_DATASOURCE_CACHE.isEmpty()) {
            LOGGER.warn("Tenant data source cache is empty during selectAnyDataSource. Initializing cache...");
            return masterDataSource;
        }

        LOGGER.info("Returning the first available DataSource from the tenant cache.");
        return TENANT_DATASOURCE_CACHE.values().iterator().next();
    }

    /**
     * Select the DataSource for the given tenant.
     * <p>
     * This method is called for each tenant identifier.
     * <p>
     * It is used to obtain a connection to the database of the tenant.
     * <p>
     * We first check if the tenant is already in the cache.
     * <p>
     * If not, we loop through all the master tenants and add them to the cache.
     * <p>
     * If the tenant is not found after the rescan, we throw a UsernameNotFoundException.
     *
     * @param tenantIdentifier the tenant identifier
     * @return the DataSource for the given tenant
     */
    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!TENANT_DATASOURCE_CACHE.containsKey(tenantIdentifier)) {
            LOGGER.warn("Tenant {} not found in the cache. Attempting to reload tenants...", tenantIdentifier);
            initializeTenantCache();
        }

        if (!TENANT_DATASOURCE_CACHE.containsKey(tenantIdentifier)) {
            LOGGER.error("Tenant {} not found after rescanning the database.", tenantIdentifier);
            throw new UsernameNotFoundException(
                    String.format("Tenant not found after rescan, tenant=%s", tenantIdentifier)
            );
        }

        LOGGER.info("Returning DataSource for tenant: {}", tenantIdentifier);
        return TENANT_DATASOURCE_CACHE.get(tenantIdentifier);
    }

    @PostConstruct
    private void initialize() {
        LOGGER.info("Initializing tenant data source cache...");
        try {
            initializeTenantCache();
            LOGGER.info("Tenant data source cache initialization completed. Total tenants loaded: {}", TENANT_DATASOURCE_CACHE.size());
        } catch (Exception ex) {
            LOGGER.error("Error initializing tenant data source cache: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    /**
     * Initializes the TENANT_DATASOURCE_CACHE by fetching tenants from the database
     * if the cache is empty.
     */
    private void initializeTenantCache() {
        if (TENANT_DATASOURCE_CACHE.isEmpty()) {
            LOGGER.info("Loading tenant information from the database...");
            List<IndexDatabase> masterTenants = indexDatabaseRepository.findAll();

            if (masterTenants.isEmpty()) {
                LOGGER.warn("No tenants found in the database. Tenant cache will remain empty.");
                return;
            }

            for (IndexDatabase masterTenant : masterTenants) {
                LOGGER.debug("Adding tenant {} to the data source cache.", masterTenant.getIdschema());
                TENANT_DATASOURCE_CACHE.computeIfAbsent(masterTenant.getIdschema(), id -> getHikariDataSource(masterTenant));
            }

            LOGGER.info("Tenant cache successfully initialized with {} tenants.", TENANT_DATASOURCE_CACHE.size());
        }
    }



}
