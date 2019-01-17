package com.mt4.api.bean;


public class UserRecord {
	// --- common settings
	private int login; // login
	private String group; // group
	private String password; // password
	// --- access flags
	private int enable; // enable
	private int enableChangePassword; // allow to change password
	private int enableReadOnly; // allow to open/positions (TRUE-may not
									// trade)
	private int enableOtp; // allow to use one-time password
	private int enableReserved; // for future use
	// ---
	private String passwordInvestor; // read-only mode password
	private String passwordPhone; // phone password
	private String name; // name
	private String country; // country
	private String city; // city
	private String state; // state
	private String zipcode; // zipcode
	private String address; // address
	private String leadSource; // lead source
	private String phone; // phone
	private String email; // email
	private String comment; // comment
	private String id; // SSN (IRD)
	private String status; // status
	private long regdate; // registration date
	private long lastdate; // last coonection time
	// --- trade settings
	private int leverage; // leverage
	private int agentAccount; // agent account
	private long timestamp; // timestamp
	private int lastIp; // last visit ip
	// ---
	private double balance; // balance
	private double prevmonthbalance; // previous month balance
	private double prevbalance; // previous day balance
	private double credit; // credit
	private double interestrate; // accumulated interest rate
	private double taxes; // taxes
	private double prevmonthequity; // previous month equity
	private double prevequity; // previous day equity
	private double reserved2; // for future use
	// ---
	private String otpSecret; // one-time password secret
	private String secureReserved; // secure data reserved
	private int sendReports; // enable send reports by email
	private int mqid; // MQ client identificator
	// private COLORREF user_color; // color got to client (used by MT Manager)
	// ---
	private String unused; // for future use
	private String apiData; // for API usage
	public int getLogin() {
		return login;
	}
	public void setLogin(int login) {
		this.login = login;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public int getEnableChangePassword() {
		return enableChangePassword;
	}
	public void setEnableChangePassword(int enableChangePassword) {
		this.enableChangePassword = enableChangePassword;
	}
	public int getEnableReadOnly() {
		return enableReadOnly;
	}
	public void setEnableReadOnly(int enableReadOnly) {
		this.enableReadOnly = enableReadOnly;
	}
	public int getEnableOtp() {
		return enableOtp;
	}
	public void setEnableOtp(int enableOtp) {
		this.enableOtp = enableOtp;
	}
	public int getEnableReserved() {
		return enableReserved;
	}
	public void setEnableReserved(int enableReserved) {
		this.enableReserved = enableReserved;
	}
	public String getPasswordInvestor() {
		return passwordInvestor;
	}
	public void setPasswordInvestor(String passwordInvestor) {
		this.passwordInvestor = passwordInvestor;
	}
	public String getPasswordPhone() {
		return passwordPhone;
	}
	public void setPasswordPhone(String passwordPhone) {
		this.passwordPhone = passwordPhone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getRegdate() {
		return regdate;
	}
	public void setRegdate(long regdate) {
		this.regdate = regdate;
	}
	public long getLastdate() {
		return lastdate;
	}
	public void setLastdate(long lastdate) {
		this.lastdate = lastdate;
	}
	public int getLeverage() {
		return leverage;
	}
	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}
	public int getAgentAccount() {
		return agentAccount;
	}
	public void setAgentAccount(int agentAccount) {
		this.agentAccount = agentAccount;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getLastIp() {
		return lastIp;
	}
	public void setLastIp(int lastIp) {
		this.lastIp = lastIp;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getPrevmonthbalance() {
		return prevmonthbalance;
	}
	public void setPrevmonthbalance(double prevmonthbalance) {
		this.prevmonthbalance = prevmonthbalance;
	}
	public double getPrevbalance() {
		return prevbalance;
	}
	public void setPrevbalance(double prevbalance) {
		this.prevbalance = prevbalance;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getInterestrate() {
		return interestrate;
	}
	public void setInterestrate(double interestrate) {
		this.interestrate = interestrate;
	}
	public double getTaxes() {
		return taxes;
	}
	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}
	public double getPrevmonthequity() {
		return prevmonthequity;
	}
	public void setPrevmonthequity(double prevmonthequity) {
		this.prevmonthequity = prevmonthequity;
	}
	public double getPrevequity() {
		return prevequity;
	}
	public void setPrevequity(double prevequity) {
		this.prevequity = prevequity;
	}
	public double getReserved2() {
		return reserved2;
	}
	public void setReserved2(double reserved2) {
		this.reserved2 = reserved2;
	}
	public String getOtpSecret() {
		return otpSecret;
	}
	public void setOtpSecret(String otpSecret) {
		this.otpSecret = otpSecret;
	}
	public String getSecureReserved() {
		return secureReserved;
	}
	public void setSecureReserved(String secureReserved) {
		this.secureReserved = secureReserved;
	}
	public int getSendReports() {
		return sendReports;
	}
	public void setSendReports(int sendReports) {
		this.sendReports = sendReports;
	}
	public int getMqid() {
		return mqid;
	}
	public void setMqid(int mqid) {
		this.mqid = mqid;
	}
	public String getUnused() {
		return unused;
	}
	public void setUnused(String unused) {
		this.unused = unused;
	}
	public String getApiData() {
		return apiData;
	}
	public void setApiData(String apiData) {
		this.apiData = apiData;
	}
	
	
	
	
}
