package com.ca.account.manager;

import com.ca.account.manager.billing.BillingConfig;
import com.ca.account.manager.common.CommonConfig;
import com.ca.account.manager.payments.PaymentsConfig;
import com.ca.account.manager.reports.ReportsConfig;
import com.ca.account.manager.tasks.TasksConfig;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({
        CommonConfig.class,
        BillingConfig.class,
        TasksConfig.class,
        PaymentsConfig.class,
        ReportsConfig.class
})
public class CoreConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
