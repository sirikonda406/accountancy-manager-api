package com.ca.account.manager.common.datasource;

import com.ca.account.manager.common.datasource.master.IndexDatabaseRepository;
import com.ca.account.manager.common.datasource.master.IndexDatabase;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages ={"com.ca.account.manager.common.datasource.master"},
        entityManagerFactoryRef = "indexEntityManagerFactory",
        transactionManagerRef = "indexTransactionManager"
)
@Configuration
public class IndexDataSourceConfig {

    private final DataSource indexDataSource;


    public IndexDataSourceConfig(DataSource indexDataSource) {
        this.indexDataSource = indexDataSource;
    }

    @Bean(name = "indexEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean indexEntityManagerFactory() {
       
        Map<String, Object> jpaPropertiesMap = new HashMap<>(new JpaProperties().getProperties());
        jpaPropertiesMap.put(AvailableSettings.FORMAT_SQL, true);
        jpaPropertiesMap.put(AvailableSettings.SHOW_SQL, true);

        return getLocalContainerEntityManagerFactoryBean(indexDataSource,jpaPropertiesMap);
    }

    private LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(DataSource indexDataSource, Map<String, Object> jpaPropertiesMap) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean= new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(indexDataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(new String[]{IndexDatabaseRepository.class.getPackageName(), IndexDatabase.class.getPackageName()});
        entityManagerFactoryBean.setJpaPropertyMap(jpaPropertiesMap);
        entityManagerFactoryBean.setPersistenceUnitName("master");
        return entityManagerFactoryBean;
    }

    @Bean(name = "indexTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("indexEntityManagerFactory") EntityManagerFactory indexEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(indexEntityManager);
        return transactionManager;
    }

}
