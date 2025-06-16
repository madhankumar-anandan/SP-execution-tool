
package com.maersk.spexecution.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;


@Component
public class BillingTransactionTemplateFactory {
	@Autowired
	@Qualifier("billingSecureDbTxnTemplate")
	private TransactionTemplate billingSecureDBTxnTemplate;

	public TransactionTemplate getSecureDbTransactionTemplate() {
		return billingSecureDBTxnTemplate;
	}

}
