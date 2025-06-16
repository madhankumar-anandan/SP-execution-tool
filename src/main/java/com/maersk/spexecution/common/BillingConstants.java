package com.maersk.spexecution.common;

public class BillingConstants {

	public static final String SCE_TOKEN = "SCETOKEN";
	// token expiry time
	public static final long TOKEN_EXPIRY_IN_MILLIS = 3600L * 1000;
	public static final int SUCCESS_CODE = 200;

	public static final String TOKEN_VALUES = "tokenValues";
	public static final String SUCCESS = "SUCCESS";
	public static final String EXPORT_LIMIT = "billing.export.limit";

	public static final String LOOKUP_ADD = "LookupsAdd";
	public static final String LOOKUP_UPDATE = "LookupsUpd";
	public static final String LOOKUP_DELETE = "LookupsDel";

	public static final String SITE_LOOKUP_ADD = "SiteLookupAdd";
	public static final String SITE_LOOKUP_UPDATE = "SiteLookupUpd";
	public static final String SITE_LOOKUP_DELETE = "SiteLookupDel";

	public static final String RECUR_CHARGES_DETAIL_FUNC = "RecurChargesDetailFunc";
	public static final String RECUR_CHARGES_HEADER_FUNC = "RecurChargesHeaderFunc";

	public static final String COLUMN_MAP_INQ = "ColumnMap_Inq";

	public static final String EVENT = "EventCodeFunc";

	public static final String INVOICE_APPROVE = "PricingApprove";

	public static final String RATE_MSG_REPROCESS = "AgreementLogReprocess";

	public static final String WMS_MSG_REPROCESS = "MWMSMessagingRepro";

	public static final String SCHEDULER_ADD = "SchedulerAdd";
	public static final String SCHEDULER_UPDATE = "SchedulerAction";
	public static final String SCHEDULER_DELETE = "SchedulerDel";

	public static final String GET_SYS_TIME_ZONE_INFO = "GetSysTimeZoneInfo";

	public static final String COMPANY_PROFILE = "CompanyProfileFunc";
	public static final String VALIDATE_QUERY = "ValidateQuery";
	public static final String GET_DROPDOWN_VALUE = "GetDropdownValue";
	public static final String GENERIC_SEARCH = "GenericSearch";
	public static final String PRICING_SUBMIT = "PricingSubmit";
	public static final String PRICING_REJECT = "PricingReject";

	public static final String JSON = "json";

	public static final String SUCCESS_STATUS_DB = "success";
	public static final String FAIL_STATUS_DB = "fail";

	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer ";

	public static final String CONTRACT_RENEWAL = "ContractRenewal";
	public static final String CONTRACT_DEL = "ContractDel";
	public static final String CONTRACT_UPD = "ContractUpd";
	public static final String CONTRACT_ADD = "ContractAdd";

	public static final String LINK_TERM_RULE = "ContractTermRuleLink";
	public static final String UNLINK_TERM_RULE = "ContractTermRuleUnlink";
	public static final String CONTRACT_TERM_RULE_RANK = "ContractTermRuleRank";

	public static final String INV_ADD = "InvoiceGroupAdd";
	public static final String INV_UPD = "InvoiceGroupUpd";
	public static final String INV_DEL = "InvoiceGroupDel";
	public static final String INV_DUP = "ContractInvoiceGroupDuplicate";

	public static final String RULE_ADD = "RuleAdd";
	public static final String RULE_UPDATE = "RuleUpdate";
	public static final String RULE_DEL = "RuleDel";

	public static final String LINK_INV_GRP_RULE = "ContractInvoiceGroupRuleLink";
	public static final String UNLINK_INV_GRP_RULE = "ContractInvoiceGroupRuleUnlink";
	public static final String UPD_INV_RNK_SEQ = "ContractInvoiceGroupRuleRank";

	public static final String ADD_OFS = "OurFinancialStrucAdd";
	public static final String UPD_OFS = "OurFinancialStrucUpd";
	public static final String DEL_OFS = "OurFinancialStrucDel";

	public static final String TERM_ADD = "TermAdd";
	public static final String TERM_UPD = "TermUpd";
	public static final String TERM_DUPLICATE = "ContractTermDuplicate";
	public static final String TERM_DEL = "TermDel";

	public static final String CHECK_PRIVILEGE_BY_BILLING_REQUEST = "CHECK_PRIVILEGE_BY_BILLING_REQUEST";

	public static final String ADD_EXCHANGE_RATE = "ExchangeRateAdd";
	public static final String UPD_EXCHANGE_RATE = "ExchangeRateUpd";
	public static final String DEL_EXCHANGE_RATE = "ExchangeRateDel";

	public static final String ADD_MERGE_RULE = "MergeRuleAdd";
	public static final String UPD_MERGE_RULE = "MergeRuleUpd";
	public static final String DEL_MERGE_RULE = "MergeRuleDel";
	public static final String UPDATE_RULE_SEQ = "MergeRuleMultipleUpd";

	public static final String ADD_CP = "ClientProfileAdd";
	public static final String UPD_CP = "ClientProfileUpd";
	public static final String DEL_CP = "ClientProfileDel";
	public static final String DEFAULT_VALUE_CP = "GetCompanyProfileDefaultValue";

	public static final String CFS = "ClientFinancialStruc";
	public static final String CAL_FUNCTION = "CalenderFunc";
	public static final String CAL_REMARKS_FUNCTION = "CalenderRemarksUpd";

	public static final String INV_ALLOCATION = "InvoicingAllocationFunc";
	public static final String INV_FOR_APPROVAL = "InvoicingSubmit";
	public static final String INV_FURTHER_REVIEW = "InvoicingFurtherReview";
	public static final String INV_REJECT = "InvoicingReject";
	public static final String INV_APPROVE = "InvoicingApprove";
	public static final String INV_SUBMIT_REVERSE = "InvoicingSubmitReverse";
	public static final String INV_CANCEL_REVERSE = "InvoicingCancelReverse";
	public static final String INV_APPROVE_REVERSE = "InvoicingApproveReverse";
	public static final String REPORT_CONFIG = "ReportConfigurationFunc";
	public static final String REPORT_URL = "ReportGetUrl";
	public static final String RE_INVOICE = "InvoicingReinvoice";
	public static final String RE_INVOICE_DET = "InvoicingReinvoiceDetail";

	public static final String TRANS_FUNC = "TransactionFunc";
	public static final String COPY_TRANS = "TransactionFuncCopyValTo";
	public static final String BILL_TRANS = "BillTransactionFunc";
	public static final String MULTI_DELETE = "TransactionMultipleDelete";

	public static final String CONFIG_MIGRATION = "ConfigMigrationAdd";
	public static final String RPT_CLASSIFICATION = "RPTClassificationFunc";
	public static final String APP_MATRIX = "ApprovalMatrixFunc";
	public static final String REACTIVATE_INV = "ReactivateInvAdd";


	public static final String ACCRUAL = "AccrualDetailFunc";
	public static final String ACCRUE = "Accrue";
	public static final String ACCRUE_SUBMIT = "AccrualSubmit";
	public static final String ACCRUE_REVIEW = "AccrualFurtherReview";
	public static final String ACCRUE_APPROVE = "AccrualApprove";
	public static final String ACCRUE_REJECT = "AccrualReject";

	public static final String ITF_TRIGGER = "ITFTriggerConfigFunc";
	public static final String INVOICE = "invoice";
	public static final String PENDING_DRAFT = "PricingDraftUpd";


	public static final String SCE_CHECK_PRIVILEGE_ENDPOINT = "/scecommon/api/users/hasPrivilegeByCodes";
	public static final String SCE_PORPERTY_ENPOINT_WITH_PARAMETERS = "/scecommon/api/core/property?propertyName=";
	public static final String SCE_COUNTRIES_URL = "/scecommon/api/app/countries";

	public static final String UTC = "UTC";
	public static final String HTTPS = "https";
	public static final String LOCALHOST_URL_8080 = "http://localhost:8080";
	public static final String LOCAL = "local";

}
