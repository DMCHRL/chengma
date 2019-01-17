package com.mt4.api.bean;

public class MarginLevel {

	private  int               login;                      // Login
	private  int               leverage;                   // Leverage
	private  int               updated;                    // Internal data
	private  double            balance;                    // Balance and credit amount
	private  double            margin;                     // Margin
	private  double            equity;                     // Equity value
	
	
	public int getLogin() {
		return login;
	}
	public void setLogin(int login) {
		this.login = login;
	}
	public int getLeverage() {
		return leverage;
	}
	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}
	public int getUpdated() {
		return updated;
	}
	public void setUpdated(int updated) {
		this.updated = updated;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getMargin() {
		return margin;
	}
	public void setMargin(double margin) {
		this.margin = margin;
	}
	public double getEquity() {
		return equity;
	}
	public void setEquity(double equity) {
		this.equity = equity;
	}
	
	
	
}
