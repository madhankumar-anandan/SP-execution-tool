package com.maersk.spexecution.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BillingDBTemplateFactory {
	@Autowired
	@Qualifier("billingSecureDbTemplate")
	private JdbcTemplate jdbcTemplateSecureDB;
	
	public JdbcTemplate getSecureDbJdbcTemplate() {
		return jdbcTemplateSecureDB;
	}

}
