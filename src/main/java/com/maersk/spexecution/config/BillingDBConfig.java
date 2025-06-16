package com.maersk.spexecution.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTransactionManagement
public class BillingDBConfig {

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
//
//	@Value("${billing.datasource.intialSize}")
//	private int intialSize;
//
//	@Value("${billing.datasource.maxActive}")
//	private int maxActive;
//
//	@Value("${billing.datasource.test-while-idle}")
//	private boolean testWhileIdle;
//
//	@Value("${billing.datasource.time-between-eviction-runs-millis}")
//	private int timeBetweenEvictionRunsMillis;
//
//	@Value("${billing.datasource.validation-query}")
//	private String validationQuery;
//
//	@Value("${billing.db.schema}")
//	private String dbSchema;
//
//	@Value("${billing.application.name}")
//	private String applicationName;
//
//	@Value("${billing.option.ansiwarning}")
//	private String ansiwarning;
//
//	@Value("${billing.option.quotedidentifier}")
//	private String quotedidentifier;
//
//	@Value("${billing.option.nocount}")
//	private String nocount;
//
//	@Value("${billing.option.concatnullyields}")
//	private String concatnullyields;

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public BillingDataSourcePropertyHolder getBillingDSPropertiesForSecurity() {
		return new BillingDataSourcePropertyHolder();
	}

	@Bean(name = "billingSecureDatabase")
	public DataSource billingSecureDataSource() {
		BillingDataSourcePropertyHolder properties = getBillingDSPropertiesForSecurity();
        return createDataSourceForBillingProperties(properties);
	}

	private BasicDataSource createDataSourceForBillingProperties(BillingDataSourcePropertyHolder properties) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(this.driverClassName);
		ds.setUrl(properties.getUrl());
//		ds.setConnectionProperties(this.applicationName);
//		List<String> options = new ArrayList<>();
//		options.add(ansiwarning);
//		options.add(quotedidentifier);
//		options.add(nocount);
//		options.add(concatnullyields);
//		ds.setConnectionInitSqls(options);
		ds.setUsername(properties.getUsername());
		ds.setPassword(properties.getPassword());
//		ds.setInitialSize(this.intialSize);
//		ds.setMaxTotal(this.maxActive);
//		ds.setTestWhileIdle(testWhileIdle);
//		ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//		ds.setValidationQuery(validationQuery);
		return ds;
	}

	@Bean(name = "billingSecureDbTemplate")
	public JdbcTemplate billingSecureJdbcTemplate(@Qualifier("billingSecureDatabase") DataSource billingSecureDb) {
		return new JdbcTemplate(billingSecureDb);
	}

	@Bean(name = "billingSecureTransactionManager")
	public PlatformTransactionManager billingSecureTransactionManager(@Qualifier("billingSecureDatabase") DataSource billingSecureDb) {
		return new DataSourceTransactionManager(billingSecureDb);
	}

	@Bean(name = "billingSecureDbTxnTemplate")
	public TransactionTemplate billingSecureDbTxnTemplate(
			@Qualifier("billingSecureTransactionManager") PlatformTransactionManager secureDbTxnMgr) {
		return new TransactionTemplate(secureDbTxnMgr);
	}

	@Setter
    @Getter
    private static class BillingDataSourcePropertyHolder {
		private String url;
		private String username;
		private String password;

    }
}
